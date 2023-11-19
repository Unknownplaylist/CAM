package UI.CampCommitteeViewManagementInterface;

import UI.CampCommitteeViewManagement.CampCommitteeView;

public interface CampCommitteeRegistrationUIInterface {

    void withdrawFromCamp(CampCommitteeView campCommitteeView);

    void registerForCamp(CampCommitteeView campCommitteeView,
            CampCommitteeMenuServiceInterface campCommitteeMenuService);

}