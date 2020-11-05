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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
