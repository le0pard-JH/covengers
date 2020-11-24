package admin.product.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;
import my.util.MyUtil;
import product.model.ProductVO;

public class InsertProductJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getContextPath();
		
		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			/*
			 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 파일을 첨부해서 보내는 폼태그가
			 * enctype="multipart/form-data" 으로 되어었다라면 HttpServletRequest request 을 사용해서는
			 * 데이터값을 받아올 수 없다. 이때는 cos.jar 라이브러리를 다운받아 사용하도록 한 후 아래의 객체를 사용해서 데이터 값 및 첨부되어진
			 * 파일까지 받아올 수 있다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			 */
			
			MultipartRequest mtrequest = null;
			
			/*
			 * MultipartRequest mtrequest 은 HttpServletRequest request 가 하던일을 그대로 승계받아서 일처리를
			 * 해주고 동시에 파일을 받아서 업로드, 다운로드까지 해주는 기능이 있다.
			 */

			// 1. 첨부되어진 파일을 디스크의 어느경로에 업로드 할 것인지 그 경로를 설정해야 한다.
			HttpSession sesssion = request.getSession();

			ServletContext svlCtx = sesssion.getServletContext();
			String imagesDir = svlCtx.getRealPath("/images");

			 System.out.println("=== 첨부되어지는 이미지 파일이 올라가는 절대경로 imagesDir ==> " +
			 imagesDir);
			// === 첨부되어지는 이미지 파일이 올라가는 절대경로 imagesDir ==>
			// C:\NCS\workspace(jsp)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MyMVC\images
			/*
			 * MultipartRequest의 객체가 생성됨과 동시에 파일 업로드가 이루어 진다.
			 * 
			 * MultipartRequest(HttpServletRequest request, String saveDirectory, -- 파일이 저장될
			 * 경로 int maxPostSize, -- 업로드할 파일 1개의 최대 크기(byte) String encoding,
			 * FileRenamePolicy policy) -- 중복된 파일명이 올라갈 경우 파일명다음에 자동으로 숫자가 붙어서 올라간다.
			 * 
			 * 파일을 저장할 디렉토리를 지정할 수 있으며, 업로드제한 용량을 설정할 수 있다.(바이트단위). 이때 업로드 제한 용량을 넘어서 업로드를
			 * 시도하면 IOException 발생된다. 또한 국제화 지원을 위한 인코딩 방식을 지정할 수 있으며, 중복 파일 처리 인터페이스를사용할 수
			 * 있다.
			 * 
			 * 이때 업로드 파일 크기의 최대크기를 초과하는 경우이라면 IOException 이 발생된다. 그러므로 Exception 처리를 해주어야
			 * 한다.
			 */
			try { 
				System.out.println("확인용1");
				// === 파일을 업로드 해준다. ===
				mtrequest = new MultipartRequest(request, imagesDir, 10485760, "UTF-8", new DefaultFileRenamePolicy());
				System.out.println("확인용2");
			} catch (IOException e) {
				
				System.out.println("확인용3");
				request.setAttribute("json", "5");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/jsonview.jsp");
				return;
			}
			System.out.println("확인용4");
			// === 첨부 이미지 파일을 올렸으니 그 다음으로 제품정보를 (제품명, 정가, 제품수량,...) DB의 tbl_product 테이블에 insert 를 해주어야 한다.  ===
			
			String productcode = mtrequest.getParameter("productcode");
			String fk_kindcode = mtrequest.getParameter("fk_kindcode");
			String fk_categorycode = mtrequest.getParameter("fk_categorycode");
			String enproductname = mtrequest.getParameter("enproductname");
			String krproductname = mtrequest.getParameter("krproductname");
			String productimg1 = mtrequest.getFilesystemName("productimg1");
			String productimg2 = mtrequest.getFilesystemName("productimg2");
			String price = mtrequest.getParameter("price");
			String saleprice = mtrequest.getParameter("saleprice");
			String origin = mtrequest.getParameter("origin");
			
			String productdescshort = mtrequest.getParameter("productdescshort");
			productdescshort = MyUtil.secureCode(productdescshort);
			
			String manufacturedate = mtrequest.getParameter("manufacturedate");
			String expiredate = mtrequest.getParameter("expiredate");
			String productdesc1 = mtrequest.getParameter("productdesc1");
			productdesc1 = MyUtil.secureCode(productdesc1);
			
			String productdesc2 = mtrequest.getParameter("productdesc2");
			productdesc2 = MyUtil.secureCode(productdesc2);
			
			String ingredient = mtrequest.getParameter("ingredient");
			ingredient = MyUtil.secureCode(ingredient);
			
			String precautions = mtrequest.getParameter("precautions");
			precautions = MyUtil.secureCode(precautions);
			
			InterAdminProductDAO adao = new AdminProductDAO();
			
			ProductVO product = new ProductVO();
			product.setProductcode(productcode);
			product.setFk_kindcode(fk_kindcode);
			product.setFk_categorycode(fk_categorycode);
			product.setEnproductname(enproductname);
			product.setKrproductname(krproductname);
			product.setProductimg1(productimg1);
			product.setProductimg2(productimg2);
			product.setPrice(Integer.parseInt(price));
			product.setSaleprice(Integer.parseInt(saleprice));
			product.setOrigin(origin);
			product.setProductdescshort(productdescshort);
			product.setManufacturedate(manufacturedate);
			product.setExpiredate(expiredate);
			product.setProductdesc1(productdesc1);
			product.setProductdesc2(productdesc2);
			product.setIngredient(ingredient);
			product.setPrecautions(precautions);
			
			int result = adao.insertProduct(product);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("result", result);
			String json = jsonObj.toString();

			// System.out.println(json);

			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}

}
