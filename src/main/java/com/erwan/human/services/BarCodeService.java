package com.erwan.human.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class BarCodeService {

    private static final int SIZE = 400;
    private static final String IMAGE_FORMAT = "png";
    private static final String OUTPUTFILE = "clone";
    private static final String OTPUTFILE_NAME = OUTPUTFILE + '.' + IMAGE_FORMAT;


    public byte[] generateQRCodeImage(String data) throws Exception {
        BitMatrix bitMatrix = generateMatrix(data);
        return writeImage(bitMatrix);
    }

    private static BitMatrix generateMatrix(String data) throws WriterException {
        return new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, BarCodeService.SIZE, BarCodeService.SIZE);
    }

    private static byte[] writeImage(BitMatrix bitMatrix) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(BarCodeService.OTPUTFILE_NAME);
        MatrixToImageWriter.writeToStream(bitMatrix, BarCodeService.IMAGE_FORMAT, fileOutputStream);
        fileOutputStream.close();
        return returnImg();
    }

    private static byte[] returnImg() throws IOException {

        InputStream targetStream = new FileInputStream(BarCodeService.OTPUTFILE_NAME);
        return IOUtils.toByteArray(targetStream);
    }

}
