package av.TestQuest1.dto;

import java.util.List;

public class AuthorityResponse {

    private List<AuthorityDTO> authorityDTOList;

    public AuthorityResponse(List<AuthorityDTO> authorityDTOList) {
        this.authorityDTOList = authorityDTOList;
    }

    public List<AuthorityDTO> getAuthorityDTOList() {
        return authorityDTOList;
    }

    public void setAuthorityDTOList(List<AuthorityDTO> authorityDTOList) {
        this.authorityDTOList = authorityDTOList;
    }
}
