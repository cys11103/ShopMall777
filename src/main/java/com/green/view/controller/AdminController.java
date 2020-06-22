package com.green.view.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.green.biz.admin.WorkerService;
import com.green.biz.admin.WorkerVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.SalesQuantity;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;
import com.green.biz.utils.Criteria;
import com.green.biz.utils.PageMaker;

@Controller
@SessionAttributes("loginWorker")
public class AdminController {

	@Autowired
	WorkerService workerService;

	@Autowired
	ProductService productService;

	@Autowired
	OrderService orderService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	QnaService qnaService;
	
	// 관리자 로그인 화면 페이지 이동
	@RequestMapping(value = "admin_login_form") // RequestMapping의 value 값은 url이다
	public String adminLoginView() {

		return "admin/main";
	}

	@RequestMapping(value = "admin_login")
	public String adminLogin(@RequestParam(value = "workerId") String id, // RequestParma은 VO의 칼럼명과 jsp의 이름(name)과 다를 경우
																			// 사용한다.
			@RequestParam(value = "workerPwd") String pwd, Model model) {

		System.out.println("admin로그인");
		int result = workerService.workerCheck(id, pwd);

		if (result == 1) {

			// 관리자 정보를 db에서 조회
			WorkerVO loginWorker = workerService.getEmployee(id);

			model.addAttribute("loginWorker", loginWorker);

			return "redirect:admin_product_list"; // redirect를 하면 데이터까지 같이 넘겨진다
		} else if (result == 0) {
			model.addAttribute("message", "비밀번호를 확인하세요");
			return "admin/main";
		} else {
			model.addAttribute("message", "아이디를 확인하세요");
			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_logout")
	public String adminLogout(SessionStatus status) {

		status.setComplete();

		return "admin/main";
	}

	@RequestMapping(value = "admin_product_list")
	public String listProduct(@RequestParam(value="key", defaultValue="") String key, 
			Criteria criteria, Model model, HttpSession session) {

		WorkerVO loginWorker = (WorkerVO) session.getAttribute("loginWorker");

		if (loginWorker == null) {
			return "admin/main";
		} else {
			System.out.println("페이지 범위:"+criteria);
			
			
			
			// 조회
			//List<ProductVO> productList = productService.listProduct(""); // "" : 빈문자열(특별하게 지정할게 없음)
			List<ProductVO> productList = productService.getListWithPaging(criteria, key);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);
			
			// 총 목록 수를 DB에서 조회
			int totalCount = productService.countProductList(key);
			pageMaker.setTotalCount(totalCount);
			System.out.println("페이징 정보:"+pageMaker);
			
			model.addAttribute("productList", productList); // "productList"는 productList.jsp에서 ${productList}임. 오른쪽 productList는 위에서 선언한 변수임
			model.addAttribute("productListSize", productList.size());					
			model.addAttribute("pageMaker", pageMaker);
			
			return "admin/product/productList";
		}
	}

	@RequestMapping(value = "admin_product_write_form")
	public String adminProductWriteView(Model model) {
		String kindList[] = { "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

		model.addAttribute("kindList", kindList);

		return "admin/product/productWrite";
	}

	@RequestMapping(value = "admin_product_write")
	public String adminProductWrite(@RequestParam(value = "product_image") MultipartFile uploadFile, ProductVO pVo,
			Model model, HttpSession session) {

		// 로그인 체크
		WorkerVO loginWorker = (WorkerVO) session.getAttribute("loginWorker");

		if (loginWorker == null) {
			return "admin/main";
		} else {
			String fileName = "";

			if (!uploadFile.isEmpty()) { // 상품 이미지가 업로드됨
				String root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/"); // session.getServletContext()가
																													// 프로젝트
																													// 폴더
																													// -
																													// webapp까지

				System.out.println("Root 경로=" + root_path);

				// 업로드된 파일명을 얻어온다.
				fileName = uploadFile.getOriginalFilename();

				try {
					File file = new File(root_path + fileName);
					uploadFile.transferTo(file);
				} catch (IllegalStateException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			pVo.setImage(fileName); // image 컬럼에 이미지 파일명 저장
			System.out.println(pVo);

			productService.insertProduct(pVo);

			return "redirect:admin_product_list";
		}
	}

	@RequestMapping(value = "admin_product_detail")
	public String adminProductDetail(ProductVO vo, Criteria criteria, Model model) { // ProductVO vo 안에 pseq가 들어옴

		String[] kindList = { "", "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

		ProductVO product = productService.getProduct(vo);

		model.addAttribute("productVO", product); // productDetail.jsp에서 사용하는 이름

		int index = Integer.parseInt(product.getKind());
		model.addAttribute("kind", kindList[index]);
		model.addAttribute("criteria", criteria);
		return "admin/product/productDetail";

	}

	@RequestMapping(value = "admin_product_update_form")
	public String adminProductUpdateView(ProductVO vo, Model model) {

		String[] kindList = { "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

		ProductVO product = productService.getProduct(vo);

		model.addAttribute("productVO", product); // productDetail.jsp에서 사용하는 이름

		model.addAttribute("kindList", kindList);

		return "admin/product/productUpdate";
	}

	@RequestMapping(value="admin_product_update")
	public String adminProductUpdate(@RequestParam(value="product_image") MultipartFile uploadFile, ProductVO vo, Model model, HttpSession session) {
		//로그인 체크
			WorkerVO loginWorker = (WorkerVO)session.getAttribute("loginWorker");
				
				if(loginWorker == null) {
					return "admin/main";
				}else {
					String fileName = "";
					
					if(!uploadFile.isEmpty()) { // 상품 이미지가 업로드됨
						String root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");  //session.getServletContext()가 프로젝트 폴더 - webapp까지
						
						System.out.println("Root 경로=" + root_path);
						
						//업로드된 파일명을 얻어온다.
						fileName = uploadFile.getOriginalFilename();
						
						try {
							File file = new File(root_path + fileName);
							uploadFile.transferTo(file);
						} catch (IllegalStateException e) {
							
							e.printStackTrace();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
						vo.setImage(fileName);
					}
					if(vo.getBestyn() == null) {
						vo.setBestyn("n");
					}
					if(vo.getUseyn() == null) {
						vo.setUseyn("n");
					}
					
					System.out.println("업데이트 정보:" +vo);
					productService.updateProduct(vo);
					
					return "redirect:admin_product_detail?pseq="+vo.getPseq();
				}
	}

	/*
	 * 주문 목록 표시
	 */
	@RequestMapping(value="admin_order_list")
	public String adminOrderList(OrderVO vo, Model model, HttpSession session){
		
		List<OrderVO> orderList =orderService.listOrder(""); //입력값 없으니 빈 문자열로 둠
		
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	/*
	 * 주문 완료 처리 (입금 확인)
	 */
	@RequestMapping(value="admin_order_save")
	public String adminOrderSave(@RequestParam(value="result") int[] odseq) { //체크박스 name
		
		//화면에서 전송한 주문 처리 체크 항목을 odseq의 배열에 저장한다.
		for(int i=0; i<odseq.length; i++) {
			orderService.updateOrderResult(odseq[i]);
		}
		
		//다시 주문리스트 부름
		return "redirect:admin_order_list";
	}
	
	/*
	 * 회원 목록 표시
	 */
	@RequestMapping(value="admin_member_list")
	public String adminMemberList(@RequestParam(value="key", defaultValue="") String name, Model model) {
		
		System.out.println("검색키="+name);
		List<MemberVO> listMember = memberService.listMember(name);
		
		//items="${memberList}"이름으로 받음 ==> memberList.jsp
		model.addAttribute("memberList", listMember); //매개변수 부분에 모델 추가
		
		return "admin/member/memberList";
		
	}
	
	/*
	 * 게시판 목록 출력
	 */
	@RequestMapping(value="admin_qna_list")
	public String adminQnaList(Model model){
		
		List<QnaVO> qnaList = qnaService.listAllQna();
		
		model.addAttribute("qnaList", qnaList);
		
		return "admin/qna/qnaList";
	}
	
	/*
	 * 게시글 상세 보기
	 */
	@RequestMapping(value="admin_qna_detail")
	public String adminQnaDetail(QnaVO vo, Model model) {
		
		//QnaVO 전체를 받아오고 그 안에서 qseq 얻어오기
		QnaVO qnaVO = qnaService.getQna(vo.getQseq());
		
		model.addAttribute("qnaVO", qnaVO);
		
		return "admin/qna/qnaDetail";
		
	}
	
	/*
	 * 답변 처리
	 */
	@RequestMapping(value="admin_qna_repsave")
	public String adminQnaRepsave(QnaVO vo, Model model) {
		
		qnaService.updateQna(vo);
		
		return "redirect:admin_qna_list";
	}
	
	/*
	 * 상품별 판매 실적
	 */
	@RequestMapping(value="admin_sales_record_form")
	public String adminProductSalesChart(Model model) {
		
		return "admin/order/salesRecords";
	}

	@RequestMapping(value="sales_record_chart", produces="application/json; charset=utf-8")
	@ResponseBody
	public List<SalesQuantity> sales_record_chart(){
		List<SalesQuantity> listSales = productService.getProductSales();
		
		System.out.println("판매 실적>>>>>>");
		System.out.println("   제품명      수량");
		System.out.println("=============");
		for(SalesQuantity item : listSales) {
			System.out.printf("%10s%3d\n", item.getPname(), item.getQuantity());
		}
		System.out.println("=============");
		return listSales;
	}
}

