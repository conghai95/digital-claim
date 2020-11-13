package com.project.dco.service.iml;

import com.project.dco.dao.ClaimRepository;
import com.project.dco.dao.UserClaimsRepository;
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

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserEndRepository userEndRepository;

    @Autowired
    private UserClaimsRepository userClaimsRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Claim createNewClaim(CreateClaimRequest createClaimRequest, MultipartFile[] multipartFiles) {
        Claim claim = new Claim();
        checkUserEndExist(createClaimRequest);
        claim.setClaimTitle(createClaimRequest.getClaimTitle());
        claim.setClaimContent(createClaimRequest.getClaimContent());
        claim.setUserClaimId(createClaimRequest.getUserClaimsId());
        claim.setUserEndId(createClaimRequest.getUserEndId());
        claimRepository.save(claim);
        fileService.uploadFile(multipartFiles);
        return claim;
    }

    @Override
    public String getClaimInfo(Integer id) {
        System.out.println(claimRepository.getClaimInfo(id).getClass());
        return null;
    }

    @Override
    public Claim addNewClaim(Claim claim) {
        try {
            Map<String, Object> nonImageVariableMap = new HashMap<>();
//            nonImageVariableMap.put("claimTitle", claim.getClaimTitle());
//            nonImageVariableMap.put("createBy", claim.getCreateBy());
//            nonImageVariableMap.put("userClaimId", claim.getUserClaimId());
//            nonImageVariableMap.put("userEndId", claim.getUserEndId());
//            nonImageVariableMap.put("claimContent", claim.getClaimContent());
            nonImageVariableMap.put("claim", claim);
            nonImageVariableMap.put("abc", "abcxyz123456");

            fileService.mergeAndGenerateOutput(TemplateEngineKind.Freemarker, nonImageVariableMap);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return null;
    }

    private void checkUserEndExist(CreateClaimRequest createClaimRequest) {
        UserEnd userEndResult = userEndRepository.getUserEndById(createClaimRequest.getUserEndId());
        if (userEndResult == null) {
            UserEnd userEnd = new UserEnd();
            userEnd.setUserEndDNI(createClaimRequest.getUserEndIdentifier());
            userEnd.setUserEndMail(createClaimRequest.getUserEmail());
            userEnd.setUserEndPassword("123456");
            userEnd.setCreateOn(DateTimeUtils.getCurrentDateString(DateTimeConstants.YYYY_MM_DD_HYPHEN));
            userEndRepository.save(userEnd);
        }
    }
}
