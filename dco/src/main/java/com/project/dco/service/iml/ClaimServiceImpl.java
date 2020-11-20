package com.project.dco.service.iml;

import com.project.dco.dao.ClaimRepository;
import com.project.dco.dao.UserEndRepository;
import com.project.dco.dto.model.Claim;
import com.project.dco.dto.model.UserEnd;
import com.project.dco.dto.request.CreateClaimRequest;
import com.project.dco.service.ClaimService;
import com.project.dco.service.FileService;
import com.project.dco_common.constants.DateTimeConstants;
import com.project.dco_common.utils.DateTimeUtils;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserEndRepository userEndRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Optional<Claim> createNewClaim(CreateClaimRequest createClaimRequest, MultipartFile[] multipartFiles) {
        Claim claim = new Claim();
        checkUserEndExist(createClaimRequest);
        claim.setClaimTitle(createClaimRequest.getClaimTitle());
        claim.setClaimContent(createClaimRequest.getClaimContent());
        claim.setUserEndId(createClaimRequest.getUserEndId());
        claimRepository.save(claim);
        fileService.uploadFile(multipartFiles);
        return Optional.of(claim);
    }

    @Override
    public String getClaimInfo(Integer id) {
        System.out.println(claimRepository.getClaimInfo(id).getClass());
        return null;
    }

    @Override
    public Optional<CreateClaimRequest> addNewClaim(CreateClaimRequest createClaimRequest) {
        try {
            Map<String, Object> nonImageVariableMap = new HashMap<>();
            nonImageVariableMap.put("createClaimRequest", createClaimRequest);

            fileService.mergeAndGenerateOutput(createClaimRequest.getTemplate(), TemplateEngineKind.Freemarker, nonImageVariableMap);
            Claim claim = new Claim();
            claim.setClaimTitle(createClaimRequest.getClaimTitle());
            claim.setClaimContent(createClaimRequest.getClaimContent());
            claim.setUserEndId(createClaimRequest.getUserEndId());
            claim.setUserClaimId(createClaimRequest.getUserClaimId());
//            claimRepository.save(claim);
            return Optional.of(createClaimRequest);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return Optional.empty();
    }

    private void checkUserEndExist(CreateClaimRequest createClaimRequest) {
        UserEnd userEndResult = userEndRepository.getUserEndById(createClaimRequest.getUserEndId());
        if (userEndResult == null) {
            UserEnd userEnd = new UserEnd();
            userEnd.setUserEndDNI(createClaimRequest.getUserEndIdentifier());
            userEnd.setUserEndPassword("123456");
            userEnd.setCreateOn(DateTimeUtils.getCurrentDateString(DateTimeConstants.YYYY_MM_DD_HYPHEN));
            userEndRepository.save(userEnd);
        }
    }
}
