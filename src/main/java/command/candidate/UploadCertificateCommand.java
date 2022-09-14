package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

public class UploadCertificateCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(UploadCertificateCommand.class);
    private final CandidateService candidateService;

    public UploadCertificateCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    private static final String UPLOAD_DIR = "D:/Final Project/Admissions_committee/src/main/webapp/certificateUploads";
    private static final int fileSizeThreshold = 1024 * 1024 * 3; // 3MB
    private static final int maxFileSize = 1024 * 1024 * 3;

    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("UploadCertificateCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("candidate.candidateProfile");

        // creates the save directory if it does not exist
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        LOG.trace("Directory for certificate uploads ==> " + fileSaveDir);
//Could I put it into the service? I must put request into parameters if so
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        LOG.debug("Request is multipart ? " + isMultiPart);
        if (isMultiPart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //size threshold after which the file will be written to disk
            factory.setSizeThreshold(fileSizeThreshold);
            factory.setRepository(fileSaveDir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            LOG.trace("Upload went correctly");
            upload.setSizeMax(maxFileSize);
            LOG.trace("Max size of upload file is ==> " + maxFileSize);
            String fileName = null;

            try {
                Map<String, List<FileItem>> parameterMap = upload.parseParameterMap(request);
                LOG.trace("Parse request parameters went successfully");
                for (List<FileItem> fileItems : parameterMap.values()) {
                    LOG.trace("List of parameters ==> " + fileItems);
                    for (FileItem fileItem : fileItems) {
                        LOG.trace("Current item name ==> " + fileItem.getName());
                        if (!fileItem.isFormField()) {
                            LOG.trace("Item is not form field");
                            fileName = new File(fileItem.getName()).getName();
                            File createdFile = new File(fileSaveDir + File.separator + fileName);
                            fileItem.write(createdFile);
                            LOG.debug("Uploaded file ==>  " + fileName);
                        }
                        LOG.trace("This List of fileItem end");
                    }
                }
                Candidate candidate = (Candidate) request.getSession().getAttribute("user");

                candidate = candidateService.saveCertificate(candidate, fileName);
                request.getSession().setAttribute("user", candidate);
                LOG.trace("Set session attribute user ==> " + candidate);
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception e) {
                LOG.trace("Exception " + e);
                request.setAttribute("message", "File Upload Failed due to " + e);
            }
        } else {
            request.setAttribute("message", "File Upload Failed because request is not multipart");
        }
        LOG.debug("UploadCertificateCommand ends by  forward to ==> " + page);
        return page;
    }
}
