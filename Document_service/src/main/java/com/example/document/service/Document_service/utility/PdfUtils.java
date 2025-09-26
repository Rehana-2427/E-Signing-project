package com.example.document.service.Document_service.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PdfUtils {

	public static String extractTextFromPdf(byte[] pdfBytes) {
		try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			return pdfStripper.getText(document);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
