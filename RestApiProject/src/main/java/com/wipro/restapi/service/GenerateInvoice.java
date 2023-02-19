package com.wipro.restapi.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wipro.restapi.entity.FoodItems;
import com.wipro.restapi.entity.OrderEntity;
import com.wipro.restapi.repository.OrderRepository;

public class GenerateInvoice {

	@Autowired
	OrderRepository orepo;
	
	public static String returnfilename(OrderEntity orderobj)
	{
		String fname="Order"+orderobj.getOrderId()+".pdf";
		Document doc = new Document();  
		try  
		{  
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("src/main/resources/static/invoices/"+fname));  
				
		doc.open();  
		//adds paragraph to the PDF file
		Font f=new Font(FontFamily.TIMES_ROMAN,30.0f,Font.BOLD,BaseColor.BLACK);
		Font f1=new Font(FontFamily.TIMES_ROMAN,15.0f,Font.ITALIC);
	
		doc.add(new Paragraph("RECEIPT FOR ORDER\n\n",f));   
		//close the PDF file  
		PdfPTable t=new PdfPTable(2);
		float[]colWidth= {10f,20f};
		t.setHorizontalAlignment(50);
		t.setWidths(colWidth);
		t.setSplitRows(true);
		PdfPCell d1=new PdfPCell(new Paragraph("Order Ref No",f1));
		d1.setFixedHeight(30f);
		d1.setHorizontalAlignment(Element.ALIGN_CENTER);
		d1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell d2=new PdfPCell(new Paragraph("Order Date",f1));
		d2.setFixedHeight(30f);
		d2.setHorizontalAlignment(Element.ALIGN_CENTER);
		d2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell d3=new PdfPCell(new Paragraph("Phone Number",f1));
		d3.setFixedHeight(30f);
		d3.setHorizontalAlignment(Element.ALIGN_CENTER);
		d3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell d4=new PdfPCell(new Paragraph("Email ID",f1));
		d4.setFixedHeight(30f);
		d4.setHorizontalAlignment(Element.ALIGN_CENTER);
		d4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell d5=new PdfPCell(new Paragraph("Amount Paayable",f1));
		d5.setFixedHeight(30f);
		d5.setHorizontalAlignment(Element.ALIGN_CENTER);
		d5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell d6=new PdfPCell(new Paragraph("Food-Items Ordered",f1));
		d6.setFixedHeight(30f);
		d6.setHorizontalAlignment(Element.ALIGN_CENTER);
		d6.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
		
		PdfPCell c1=new PdfPCell(new Paragraph(orderobj.getEmail()));
		c1.setFixedHeight(30f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell c2=new PdfPCell(new Paragraph(orderobj.getPhno()));
		c2.setFixedHeight(30f);
		c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		double d=orderobj.getTotalPlusGST();
		PdfPCell c3=new PdfPCell(new Paragraph("Rs."+d));
		c3.setFixedHeight(30f);
		c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		int oid=orderobj.getOrderId();
		PdfPCell c4=new PdfPCell(new Paragraph(String.valueOf(oid)));
		c4.setFixedHeight(30f);
		c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell c5=new PdfPCell(new Paragraph(orderobj.getOrderDate().toString()));
		c5.setFixedHeight(30f);
		c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		List <String> fl2=new ArrayList<>();
		List <FoodItems> f2=orderobj.getCart().getListofitems();
		for(FoodItems a:f2)
		{
			fl2.add(a.getFoodName());
		}
		PdfPCell c6=new PdfPCell(new Paragraph(fl2.toString()));
		c6.setFixedHeight(30f);
		c6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		t.addCell(d1);
		t.addCell(c4);
		t.addCell(d2);
		t.addCell(c5);
		t.addCell(d3);
		t.addCell(c2);
		t.addCell(d4);
		t.addCell(c1);
		t.addCell(d5);
		t.addCell(c3);
		t.addCell(d6);
		t.addCell(c6);
		
		doc.add(t);
		doc.close();  
		//closes the writer  
		writer.close();  
		}   
		catch (DocumentException|FileNotFoundException e)  
		{  
		e.printStackTrace();  
		}   
		
		
		return fname;
	}
}
