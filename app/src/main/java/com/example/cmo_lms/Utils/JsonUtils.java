package com.example.cmo_lms.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.cmo_lms.model.Searchref_noResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {

    private static final Map<String, String> repTypeMap = new LinkedHashMap<>();
    static Map<String, String> resultMap = new LinkedHashMap<>();

    static {
        repTypeMap.put("5", "ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳು(ಸಿಎಂ- ಟಿಪ್ಪಣಿ)");
        repTypeMap.put("1", "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ");
        repTypeMap.put("3", "ಶಾಸಕರು - ವಿಧಾನ ಪರಿಷತ್‌");
        repTypeMap.put("2", "ಲೋಕಸಭಾ ಸದಸ್ಯರು");
        repTypeMap.put("4", "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರು");
        repTypeMap.put("6", "ಮಾಜಿ ಶಾಸಕರು – ವಿಧಾನ ಪರಿಷತ್ತು");
        repTypeMap.put("7", "ಮಾಜಿ ಶಾಸಕರು – ಲೋಕಸಭಾ");
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getRepTypeName(String id) {
        return repTypeMap.get(id);
    }

    public static Pair<String[], Map<String, Map<String, Integer>>> convertJsonToMap(String jsonString) {
        List<String> namesList = new ArrayList<>();
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        try {
            JsonArray jsonArray = new Gson().fromJson(jsonString, JsonArray.class);

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String name = jsonObject.get("name").getAsString();

                String currentLanguage = getCurrentLanguage();
                Log.d("Current Language", currentLanguage);

                if (Objects.equals(name, "CMO/CM")) {
                    name = "Hon' Chief Minister (CM)";
                }

                if (Objects.equals(name, "MPL")) {
                    name = "MP-Lok Sabha";
                }

                if (Objects.equals(name, "MPR")) {
                    name = "MP-Rajya Sabha";
                }

                if (Objects.equals(name, "CMO/MLC")) {
                    name = "MLC";
                }

                if (currentLanguage.equals("kn")) {
                    switch (name) {
                        case "Hon' Chief Minister (CM)":
                            name = "ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳು";
                            break;
                        case "MLA":
                            name = "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ";
                            break;
                        case "MLC":
                            name = "ಶಾಸಕರು - ವಿಧಾನ ಪರಿಷತ್‌";
                            break;
                        case "MP-Lok Sabha":
                            name = "ಲೋಕಸಭಾ ಸದಸ್ಯರು";
                            break;
                        case "MP-Rajya Sabha":
                            name = "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರು";
                            break;
                        case "EX-MLC":
                            name = "ಮಾಜಿ ಶಾಸಕರು – ವಿಧಾನ ಪರಿಷತ್ತು";
                            break;
                        default:
                            break;
                    }
                }

                namesList.add(name);

                Map<String, Integer> countsMap = new HashMap<>();

                if (currentLanguage.equals("kn")) {
                    countsMap.put("ಎಣಿಕೆ", jsonObject.get("Count").getAsInt());
                    countsMap.put("ಬಾಕಿಯಿರುವ ಎಣಿಕೆ", jsonObject.get("PendingCount").getAsInt());
                    countsMap.put("ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ಸ್ವೀಕರಿಸಲಾಗಿದೆ", jsonObject.get("AcceptedClosedCount").getAsInt());
                    countsMap.put("ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ತಿರಸ್ಕರಿಸಲಾಗಿದೆ", jsonObject.get("RejectedClosedCount").getAsInt());
                    countsMap.put("ಮುಚ್ಚಿದ ಎಣಿಕೆ", jsonObject.get("ClosedCount").getAsInt());
                } else {
                    countsMap.put("Count", jsonObject.get("Count").getAsInt());
                    countsMap.put("Pending Count", jsonObject.get("PendingCount").getAsInt());
                    countsMap.put("Accepted Closed Count", jsonObject.get("AcceptedClosedCount").getAsInt());
                    countsMap.put("Rejected Closed Count", jsonObject.get("RejectedClosedCount").getAsInt());
                    countsMap.put("Closed Count", jsonObject.get("ClosedCount").getAsInt());
                }

                resultMap.put(name, countsMap);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return new Pair<>(namesList.toArray(new String[0]), resultMap);
    }

    public static Map<String, String> convertJsonStringToMap(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Searchref_noResponseModel>() {
        }.getType();
        Searchref_noResponseModel letterData = gson.fromJson(jsonString, type);

        return letterDataToMap(letterData);
    }

    private static Map<String, String> letterDataToMap(Searchref_noResponseModel receivedModel) {

        resultMap.put("Rg ID :", String.valueOf(receivedModel.getRgId()));
        resultMap.put("Representative No :", receivedModel.getRgRef());
        resultMap.put("Date :", receivedModel.getRgDate());
        resultMap.put("Letter No :", receivedModel.getRgLetterNo());
        resultMap.put("Representative Name :", String.valueOf(receivedModel.getRgRepresentativeId()));
        resultMap.put("Representative Mob :", receivedModel.getRgRepresentativeMob());
        resultMap.put("Representative Address :", receivedModel.getRgAddress1());
        resultMap.put("Representative Griv Category :", String.valueOf(receivedModel.getRgGrivCategoryId()));
        resultMap.put("Attachment :", receivedModel.getRgAttachementPath());
        resultMap.put("CM Note Path :", receivedModel.getReCmNotePath());
        resultMap.put("Letter Description :", receivedModel.getRgGrievanceDesc());
        resultMap.put("Forwarded Dept Id :", String.valueOf(receivedModel.getRgForwardedDeptId()));
        resultMap.put("Forwarded LineDepartment ID :", String.valueOf(receivedModel.getRgForwardedLinedepartmentId()));
        resultMap.put("Forwarded District ID :", String.valueOf(receivedModel.getRgForwardedDistrictId()));
        resultMap.put("Status :", mapActionType(receivedModel.getRgCmogActionType()));
        resultMap.put("Closure Date :", receivedModel.getRgClosureDate());
        resultMap.put("Closure Description :", receivedModel.getRgClosureDescription());
        resultMap.put("Post Name :", String.valueOf(receivedModel.getRgPostId()));
        resultMap.put("Rg is Atr Filled :", String.valueOf(receivedModel.getRgIsAtrFilled()));
        resultMap.put("Rg is Active :", String.valueOf(receivedModel.getRgIsActive()));
        resultMap.put("Rg Created On :", receivedModel.getRgCreatedOn());
        resultMap.put("Rg Created By :", String.valueOf(receivedModel.getRgCreatedBy()));
        resultMap.put("Rg Updated On :", receivedModel.getRgUpdatedOn());
        resultMap.put("Rg Updated By :", String.valueOf(receivedModel.getRgUpdatedBy()));
        resultMap.put("Representative Type :", getRepTypeName(String.valueOf(receivedModel.getRepType())));
        resultMap.put("MLA Constituency :", String.valueOf(receivedModel.getMlaConstituency()));
        resultMap.put("MP-Lok Sabha Constituency :", String.valueOf(receivedModel.getMplConstituency()));
        resultMap.put("MP-Rajya Sabha Constituency :", String.valueOf(receivedModel.getMprConstituency()));
        resultMap.put("Ex MLC Constituency :", String.valueOf(receivedModel.getExMlcConstituency()));
        resultMap.put("MLC Constituency :", String.valueOf(receivedModel.getMlcConstituency()));
        resultMap.put("CM Remark :", receivedModel.getRgCmRemark());
        resultMap.put("Priority :", String.valueOf(receivedModel.getRgPriority()));
        resultMap.put("Number of Days :", String.valueOf(receivedModel.getRgNoDays()));
        resultMap.put("eOffice Receipt Cmp No :", String.valueOf(receivedModel.getEofficeReciptCmpNo()));
        resultMap.put("eOffice Status :", receivedModel.getEofficeStatus());
        resultMap.put("eOffice Receipt No :", receivedModel.getEofficeReceiptNo());
        resultMap.put("eOffice Currently With :", receivedModel.getEofficeCurrenetlyWith());
        resultMap.put("eOffice Since When :", String.valueOf(receivedModel.getEofficeSinceWhen()));
        resultMap.put("eOffice Closing Remarks :", receivedModel.getEofficeClosingRemarks());
        resultMap.put("eOffice FileNumber :", receivedModel.getEofficeFilenumber());
        resultMap.put("eOffice File Cmp No :", receivedModel.getEofficeFileCmpNo());
        resultMap.put("eOffice Receipt Updated On :", receivedModel.getEofficeReciptUpdatedOn());
        resultMap.put("eOffice Dep Code :", String.valueOf(receivedModel.getEofficeDepCode()));

        return resultMap;
    }

    private static String mapActionType(int actionTypeId) {
        switch (actionTypeId) {
            case 1:
                return "Closed";
            case 2:
                return "Forwarded";
            default:
                return "Empty";
        }
    }

    public static void generatePdf_summary(Map<String, Map<String, Integer>> pdfData, Context context) {
        PdfDocument pdfDocument = new PdfDocument();
        int pageWidth = 250;
        int pageHeight = 500;
        int borderWidth = 2; // Border width
        int padding = 10; // Padding inside the border
        int topPaddingInsideBorder = 10; // Additional padding at the top inside the border

        Paint keyPaint = new Paint();
        keyPaint.setTextSize(8);
        keyPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint valuePaint = new Paint();
        valuePaint.setTextSize(6);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int lineHeight = 15;
        int yPosition = lineHeight + borderWidth + padding + topPaddingInsideBorder; // Start drawing from padding

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(Color.BLACK);
        canvas.drawRect(borderWidth + padding, borderWidth + padding, pageWidth - borderWidth - padding, pageHeight - borderWidth - padding, borderPaint);

        String[] outerKeysOrder;

        if (LanguageUtil.getCurrentLanguage().equals("en")) {
            outerKeysOrder = new String[]{"Hon' Chief Minister (CM)", "MLA", "MLC", "MP-Lok Sabha", "MP-Rajya Sabha", "EX-MLC"};
        } else {
            outerKeysOrder = new String[]{"ಮಾನ್ಯ ಮುಖ್ಯಮಂತ್ರಿಗಳು", "ಶಾಸಕರು - ವಿಧಾನ ಸಭೆ", "ಶಾಸಕರು - ವಿಧಾನ ಪರಿಷತ್‌", "ಲೋಕಸಭಾ ಸದಸ್ಯರು", "ರಾಜ್ಯಸಭಾ ಸದಸ್ಯರು", "ಮಾಜಿ ಶಾಸಕರು – ವಿಧಾನ ಪರಿಷತ್ತು"};
        }
        for (String outerKey : outerKeysOrder) {
            Map<String, Integer> innerMap = pdfData.get(outerKey);
            if (innerMap != null) {
                // Draw the outer key (bold)
                canvas.drawText(outerKey, borderWidth + padding + 10, yPosition, keyPaint);
                yPosition += lineHeight;

                String[] innerKeysOrder;
                if (LanguageUtil.getCurrentLanguage().equals("en")) {
                    innerKeysOrder = new String[]{"Count", "Pending Count", "Accepted Closed Count", "Rejected Closed Count", "Closed Count"};
                } else {
                    innerKeysOrder = new String[]{"ಎಣಿಕೆ", "ಬಾಕಿಯಿರುವ ಎಣಿಕೆ", "ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ಸ್ವೀಕರಿಸಲಾಗಿದೆ", "ಮುಚ್ಚಿದ ಎಣಿಕೆಯನ್ನು ತಿರಸ್ಕರಿಸಲಾಗಿದೆ", "ಮುಚ್ಚಿದ ಎಣಿಕೆ"};
                }
                // Draw inner keys and values in the specified order
                for (String innerKey : innerKeysOrder) {
                    if (innerMap.containsKey(innerKey)) {
                        Integer value = innerMap.get(innerKey);
                        // Draw inner key and value
                        String text = innerKey + "  :  " + value;
                        canvas.drawText(text, borderWidth + padding + 20, yPosition, valuePaint);
                        yPosition += lineHeight;

                        if (yPosition + lineHeight > pageHeight - borderWidth - padding) {
                            pdfDocument.finishPage(page);
                            pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pdfDocument.getPages().size() + 1).create();
                            page = pdfDocument.startPage(pageInfo);
                            canvas = page.getCanvas();
                            canvas.drawRect(borderWidth + padding, borderWidth + padding, pageWidth - borderWidth - padding, pageHeight - borderWidth - padding, borderPaint);
                            yPosition = lineHeight + borderWidth + padding + topPaddingInsideBorder;
                        }
                    }
                }
            }
        }


        pdfDocument.finishPage(page);
        savePdf(pdfDocument, context, "Summary"); // Provide a default name for the PDF
        pdfDocument.close();
    }


    public static void generatePdf(Map<String, String> pdfData, Context context) {
        Log.e("generate pdf data", String.valueOf(pdfData));
        PdfDocument pdfDocument = new PdfDocument();
        int pageWidth = 250;
        int pageHeight = 500;
        int borderWidth = 2; // Border width
        int padding = 10; // Padding inside the border
        int topPaddingInsideBorder = 10; // Additional padding at the top inside the border

        Paint keyPaint = new Paint();
        keyPaint.setTextSize(8);
        keyPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint valuePaint = new Paint();
        valuePaint.setTextSize(6);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int lineHeight = 15;
        int yPosition = lineHeight + borderWidth + padding + topPaddingInsideBorder; // Start drawing from padding

        // Draw border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(Color.BLACK);
        canvas.drawRect(borderWidth + padding, borderWidth + padding, pageWidth - borderWidth - padding, pageHeight - borderWidth - padding, borderPaint);

        boolean isFirstEntry = true;
        for (Map.Entry<String, String> entry : pdfData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key != null && value != null && !value.equalsIgnoreCase("") && !value.equalsIgnoreCase("null") && !shouldSkipKey(key)) {
                // Calculate the width of the key
                float keyWidth = keyPaint.measureText(key + "  ");

                if (isFirstEntry) {
                    // Draw the first key and value with highlighting color
                    keyPaint.setColor(Color.RED);
                    valuePaint.setColor(Color.RED);
                }
                // Calculate the available width for the value considering the padding and key width
                float availableValueWidth = pageWidth - 2 * (borderWidth + padding) - keyWidth - 20;

                // Split the value text into multiple lines if it exceeds the available width
                String[] valueLines = splitTextIntoLines(value, (int) availableValueWidth, valuePaint);

                // Draw key (bold) and value (normal) on the same line, with padding
                canvas.drawText(key, borderWidth + padding + 10, yPosition, keyPaint);

                // Calculate the X position for drawing the value with additional padding
                float valueXPosition = borderWidth + padding + 10 + keyWidth;

                // Draw the first line of the value
                canvas.drawText(valueLines[0], valueXPosition, yPosition, valuePaint);

                // Increment the Y position for the next line
                yPosition += lineHeight;

                // Draw remaining lines of value on new lines, ensuring they do not cross the border
                for (int i = 1; i < valueLines.length; i++) {
                    if (yPosition + lineHeight < pageHeight - borderWidth - padding) {
                        // Draw the subsequent lines of the value with the adjusted X position
                        canvas.drawText(valueLines[i], valueXPosition, yPosition, valuePaint);
                        // Increment the Y position for the next line
                        yPosition += lineHeight;
                    } else {
                        // If the current page is full, start a new page
                        pdfDocument.finishPage(page);
                        pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pdfDocument.getPages().size() + 1).create();
                        page = pdfDocument.startPage(pageInfo);
                        canvas = page.getCanvas();

                        // Draw border for the new page
                        canvas.drawRect(borderWidth + padding, borderWidth + padding, pageWidth - borderWidth - padding, pageHeight - borderWidth - padding, borderPaint);

                        // Reset Y position for the new page
                        yPosition = lineHeight + borderWidth + padding + topPaddingInsideBorder;

                        float keyXPosition = (pageWidth - keyWidth) / 2;

                        float valueXaPosition = borderWidth + padding + keyWidth + 20;

                        canvas.drawText(key, keyXPosition, yPosition, keyPaint);
                        canvas.drawText(valueLines[0], valueXaPosition, yPosition, valuePaint);

                        // Increment Y position for the next line
                        yPosition += lineHeight;
                    }
                }

                if (isFirstEntry) {
                    keyPaint.setColor(Color.BLACK);
                    valuePaint.setColor(Color.BLACK);
                    isFirstEntry = false;
                }
            }
        }

        pdfDocument.finishPage(page);
        savePdf(pdfDocument, context, pdfData.get("Letter No :"));
        pdfDocument.close();
    }

    private static String[] splitTextIntoLines(String text, int maxWidth, Paint paint) {
        List<String> lines = new ArrayList<>();
        if (text != null && !text.isEmpty()) {
            int start = 0;
            int end;
            while (start < text.length()) {
                end = paint.breakText(text, start, text.length(), true, maxWidth, null);
                lines.add(text.substring(start, start + end));
                start += end;
            }
        }
        return lines.toArray(new String[0]);
    }

    private static boolean shouldSkipKey(String key) {
        List<String> keysToSkip = Arrays.asList("Attachment :", "Rg ID :", "Rg is Atr Filled :", "Rg is Active :", "Rg Created On :", "Rg Created By :", "Rg Updated On :", "Rg Updated By :", "eOffice Dep Code :");
        return keysToSkip.contains(key);
    }

    private static void savePdf(PdfDocument pdfDocument, Context context, String letter_name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String endPart = sanitizeFileName(letter_name);

        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/RepresentativesGrievance/SupportingDocuments/";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdirs();
            if (!directoryCreated) {
                showToast(context, "Error creating directory.");
                return;
            }
        }

        String pdfFileName = endPart + "(" + timeStamp + ")" + ".pdf";

        File pdfFile = new File(directory, pdfFileName);

        try {
            boolean fileCreated = pdfFile.createNewFile();
            if (!fileCreated) {
                showToast(context, "Error creating PDF file.");
                return;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fileOutputStream);
            fileOutputStream.close();
            showToast(context, "PDF saved to: " + pdfFile.getAbsolutePath());

            // Notify the system that a new file has been created and should be visible in the Downloads app
            MediaScannerConnection.scanFile(context, new String[]{pdfFile.getAbsolutePath()}, null, null);

        } catch (IOException e) {
            e.printStackTrace();
            showToast(context, "Error saving PDF.");
        }
    }

    private static String sanitizeFileName(String fileName) {
        // Remove any invalid characters such as "/", "\", ":", "*", "?", "\"", "<", ">", "|"
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "-");
    }

    private static void showToast(Context context, String message) {
        // Display a toast message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

