package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Repository.AimRepository;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPA;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository.CAPARepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Dashboard;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.CWFRepository;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEA;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository.FMEARepository;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.IB.Repository.IBEntityRepository;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOC;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Repository.MOCRepository;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Repository.RCARepository;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import com.maxbyte.sam.SecondaryDBFlow.SA.Repository.SARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private CWFRepository cwfRepository;//2
    @Autowired
    private RCARepository rcaRepository;//9
    @Autowired
    private AimRepository aimRepository;//no pending
    @Autowired
    private CAPARepository capaRepository;//4
    @Autowired
    private IBEntityRepository ibEntityRepository;//3
    @Autowired
    private MOCRepository mocRepository;//8 or 9
    @Autowired
    private FMEARepository fmeaRepository;//2
    @Autowired
    private SARepository saRepository;




   public ResponseModel<List<Object>> getListCounts() {
        List<Object> countsList = new ArrayList<>();



        //RCA Status
        List<RCA> rcaList = rcaRepository.findAll();
        List<RCA> openRCAs=rcaRepository.findOpenRCAs();
        List<RCA> inProgressRCAs = rcaRepository.findInProgressRCAs();
        List<RCA> completeRCAs = rcaRepository.findCompleteRCAs();
        List<RCA> pendingRCAs = rcaRepository.findPendingRCAs();
        List<RCA> revertBackRCAs = rcaRepository.findRevertBackRCAs();
        List<RCA> closedRCAs = rcaRepository.findClosedRCAs();

        int rcaTotalCount = rcaList.size();
        int openCount = openRCAs.size();
        int inProgressCount = inProgressRCAs.size();
        int completedCount = completeRCAs.size();
        int pendingCount = pendingRCAs.size();
        int revertBackCount = revertBackRCAs.size();
        int closedCount = closedRCAs.size();
        countsList.add(new Dashboard("RCA", rcaTotalCount, openCount,inProgressCount,completedCount,pendingCount,revertBackCount,closedCount));

        // CAPA Status

        List<CAPA> capaList = capaRepository.findAll();
        List<CAPA> openCAPAs=capaRepository.findOpenCAPAs();
        List<CAPA> inProgressCAPAs = capaRepository.findInProgressCAPAs();
        List<CAPA> completeCAPAs = capaRepository.findCompleteCAPAs();
        List<CAPA> pendingCAPAs = capaRepository.findPendingCAPAs();
        List<CAPA> revertBackCAPAs = capaRepository.findRevertBackCAPAs();
        List<CAPA> closedCAPAs = capaRepository.findClosedCAPAs();

        int capaTotalCount = capaList.size();
        int capaOpenCount = openCAPAs.size();
        int capaInProgressCount = inProgressCAPAs.size();
        int capaCompletedCount = completeCAPAs.size();
        int capaPendingCount = pendingCAPAs.size();
        int capaRevertBackCount = revertBackCAPAs.size();
        int capaClosedCount = closedCAPAs.size();
        countsList.add(new Dashboard("CAPA", capaTotalCount, capaOpenCount,capaInProgressCount,capaCompletedCount,capaPendingCount,capaRevertBackCount,capaClosedCount));

        // MOC Status

        List<MOC> MOCList = mocRepository.findAll();
        List<MOC> openMOCs=mocRepository.findOpenMOCs();
        List<MOC> inProgressMOCs = mocRepository.findInProgressMOCs();
        List<MOC> completeMOCs = mocRepository.findCompleteMOCs();
        List<MOC> revertBackMOCs = mocRepository.findRevertBackMOCs();
        List<MOC> closedMOCs = mocRepository.findClosedMOCs();

        int mocTotalCount = MOCList.size();
        int mocOpenCount = openMOCs.size();
        int mocInProgressCount = inProgressMOCs.size();
        int mocCompletedCount = completeMOCs.size();
        int mocRevertBackCount = revertBackMOCs.size();
        int mocClosedCount = closedMOCs.size();
        countsList.add(new Dashboard("MOC", mocTotalCount, mocOpenCount,mocInProgressCount,mocCompletedCount,null,mocRevertBackCount,mocClosedCount));

        //FMEA Status

        List<FMEA> FMEAList = fmeaRepository.findAll();
        List<FMEA> openFMEAs = fmeaRepository.findOpenFMEAs();
        List<FMEA> inProgressFMEAs = fmeaRepository.findInProgressFMEAs();
        List<FMEA> completeFMEAs = fmeaRepository.findCompleteFMEAs();
        List<FMEA> revertBackFMEAs = fmeaRepository.findRevertBackFMEAs();
        List<FMEA> closedFMEAs = fmeaRepository.findClosedFMEAs();

        int fmeaTotalCount = FMEAList.size();
        int fmeaOpenCount = openFMEAs.size();
        int fmeaInProgressCount = inProgressFMEAs.size();
        int fmeaCompletedCount = completeFMEAs.size();
        int fmeaRevertBackCount = revertBackFMEAs.size();
        int fmeaClosedCount = closedFMEAs.size();
        countsList.add(new Dashboard("FMEA", fmeaTotalCount, fmeaOpenCount, fmeaInProgressCount, fmeaCompletedCount, null, fmeaRevertBackCount, fmeaClosedCount));

        //IB Module
        List<IB> ibList = ibEntityRepository.findAll();
        List<IB> openIB = ibEntityRepository.findOpenIB();
        List<IB> inProgressIB = ibEntityRepository.findInProgressIB();
        List<IB> completeIB = ibEntityRepository.findCompleteIB();
        List<IB> pendingIB = ibEntityRepository.findPendingIB();
        List<IB> revertBackIB = ibEntityRepository.findRevertBackIB();
        List<IB> closedIB = ibEntityRepository.findClosedIB();

        int ibTotalCount = ibList.size();
        int openCountIB = openIB.size();
        int inProgressCountIB = inProgressIB.size();
        int completedCountIB = completeIB.size();
        int pendingCountIB = pendingIB.size();
        int revertBackCountIB = revertBackIB.size();
        int closedCountIB = closedIB.size();
        countsList.add(new Dashboard("IB", ibTotalCount, openCountIB,inProgressCountIB,completedCountIB,pendingCountIB,revertBackCountIB,closedCountIB));

        //AIM Module
        List<Aim> aimList = aimRepository.findAll();
        List<Aim> openAIM= aimRepository.findOpenAim();
        List<Aim> revertBackAIM = aimRepository.findRevertBackAim();
        List<Aim> closedAIM = aimRepository.findClosedAim();

        int aimTotalCount = aimList.size();
        int aimOpenCount = openAIM.size();
        int aimRevertBackCount = revertBackAIM.size();
        int aimClosedCount = closedAIM.size();
        countsList.add(new Dashboard("AIM", aimTotalCount, aimOpenCount,null,null,null,aimRevertBackCount,aimClosedCount));


        //CWF Module
        List<CWF> cwfList = cwfRepository.findAll();
        List<CWF> openCWF= cwfRepository.findOpenCWF();
        List<CWF> completeCWF = cwfRepository.findCompleteCWF();
        List<CWF> pendingCWF = cwfRepository.findPendingCWF();
        List<CWF> revertBackCWF = cwfRepository.findRevertBackCWF();
        List<CWF> closedCWF = cwfRepository.findClosedCWF();

        int cwfTotalCount = cwfList.size();
        int cwfOpenCount = openCWF.size();
        int cwfCompletedCount = completeCWF.size();
        int cwfPendingCount = pendingCWF.size();
        int cwfRevertBackCount = revertBackCWF.size();
        int cwfClosedCount = closedCWF.size();
        countsList.add(new Dashboard("CWF", cwfTotalCount, cwfOpenCount,null,cwfCompletedCount,cwfPendingCount,cwfRevertBackCount,cwfClosedCount));

        //SA Module
        List<SA> saList = saRepository.findAll();
        List<SA> openSA= saRepository.findOpenSAs();
        List<SA> approvedSA = saRepository.findApprovedSAs();
        List<SA> pendingSA = saRepository.findApproverPendingSAs();
//        List<SA> revertBackCWF = cwfRepository.findRevertBackCWF();
//        List<SA> closedCWF = cwfRepository.findClosedCWF();

        int saTotalCount = saList.size();
        int saOpenCount = openSA.size();
        int saPendingCount = approvedSA.size();
        int saApprovedCount = pendingSA.size();
//        int cwfRevertBackCount = revertBackCWF.size();
//        int cwfClosedCount = closedCWF.size();
        countsList.add(new Dashboard("CWF", saTotalCount, saOpenCount,null,saPendingCount,saApprovedCount,null,null));



        return new ResponseModel<>(true, "Success", countsList);
    }


    public ResponseModel<List<Object>> getListCounts(String username, String organizationCode) {
        List<Object> countsList = new ArrayList<>();

        //RCA Status

        List<RCA> openRCAs = rcaRepository.findOpenRCAs(username, organizationCode);
        List<RCA> inProgressRCAs = rcaRepository.findInProgressRCAs(username, organizationCode);
        List<RCA> completeRCAs = rcaRepository.findCompleteRCAs(username, organizationCode);
        List<RCA> pendingRCAs = rcaRepository.findPendingRCAs(username, organizationCode);
        List<RCA> revertBackRCAs = rcaRepository.findRevertBackRCAs(username, organizationCode);
        List<RCA> closedRCAs = rcaRepository.findClosedRCAs(username, organizationCode);

        int openCount = openRCAs.size();
        int inProgressCount = inProgressRCAs.size();
        int completedCount = completeRCAs.size();
        int pendingCount = pendingRCAs.size();
        int revertBackCount = revertBackRCAs.size();
        int closedCount = closedRCAs.size();

        int rcaTotalCount = openCount + inProgressCount + completedCount + pendingCount + revertBackCount + closedCount;

        countsList.add(new Dashboard("RCA", rcaTotalCount, openCount, inProgressCount, completedCount, pendingCount, revertBackCount, closedCount));

        // CAPA Status

        List<CAPA> openCAPAs = capaRepository.findOpenCAPAs(username,organizationCode);
        List<CAPA> inProgressCAPAs = capaRepository.findInProgressCAPAs(username,organizationCode);
        List<CAPA> completeCAPAs = capaRepository.findCompleteCAPAs(username,organizationCode);
        List<CAPA> pendingCAPAs = capaRepository.findPendingCAPAs(username,organizationCode);
        List<CAPA> revertBackCAPAs = capaRepository.findRevertBackCAPAs(username,organizationCode);
        List<CAPA> closedCAPAs = capaRepository.findClosedCAPAs(username,organizationCode);

        int capaOpenCount = openCAPAs.size();
        int capaInProgressCount = inProgressCAPAs.size();
        int capaCompletedCount = completeCAPAs.size();
        int capaPendingCount = pendingCAPAs.size();
        int capaRevertBackCount = revertBackCAPAs.size();
        int capaClosedCount = closedCAPAs.size();

        int capaTotalCount = capaOpenCount+capaInProgressCount+capaCompletedCount+capaPendingCount+capaRevertBackCount+capaClosedCount;

        countsList.add(new Dashboard("CAPA", capaTotalCount, capaOpenCount, capaInProgressCount, capaCompletedCount, capaPendingCount, capaRevertBackCount, capaClosedCount));



        //MOC Status

        List<MOC> openMOCs = mocRepository.findOpenMOCs(username,organizationCode);
        List<MOC> inProgressMOCs = mocRepository.findInProgressMOCs(username,organizationCode);
        List<MOC> completeMOCs = mocRepository.findCompleteMOCs(username,organizationCode);
        List<MOC> revertBackMOCs = mocRepository.findRevertBackMOCs(username,organizationCode);
        List<MOC> closedMOCs = mocRepository.findClosedMOCs(username,organizationCode);

        int mocOpenCount = openMOCs.size();
        int mocInProgressCount = inProgressMOCs.size();
        int mocCompletedCount = completeMOCs.size();
        int mocRevertBackCount = revertBackMOCs.size();
        int mocClosedCount = closedMOCs.size();

        int mocTotalCount = mocOpenCount + mocInProgressCount + mocCompletedCount  + mocRevertBackCount + mocClosedCount;

        countsList.add(new Dashboard("MOC", mocTotalCount, mocOpenCount, mocInProgressCount, mocCompletedCount, null, mocRevertBackCount, mocClosedCount));


        //FMEA Status

        List<FMEA> openFMEAs = fmeaRepository.findOpenFMEAs(username,organizationCode);
        List<FMEA> inProgressFMEAs = fmeaRepository.findInProgressFMEAs(username,organizationCode);
        List<FMEA> completeFMEAs = fmeaRepository.findCompleteFMEAs(username,organizationCode);
        List<FMEA> revertBackFMEAs = fmeaRepository.findRevertBackFMEAs(username,organizationCode);
        List<FMEA> closedFMEAs = fmeaRepository.findClosedFMEAs(username,organizationCode);

        int fmeaOpenCount = openFMEAs.size();
        int fmeaInProgressCount = inProgressFMEAs.size();
        int fmeaCompletedCount = completeFMEAs.size();
        int fmeaRevertBackCount = revertBackFMEAs.size();
        int fmeaClosedCount = closedFMEAs.size();

        int fmeaTotalCount = fmeaOpenCount+fmeaInProgressCount+fmeaCompletedCount+fmeaRevertBackCount+fmeaClosedCount;

        countsList.add(new Dashboard("FMEA", fmeaTotalCount, fmeaOpenCount, fmeaInProgressCount, fmeaCompletedCount, null, fmeaRevertBackCount, fmeaClosedCount));


        //IB status

        List<IB> openIB = ibEntityRepository.findOpenIB(username,organizationCode);
        List<IB> inProgressIB = ibEntityRepository.findInProgressIB(username,organizationCode);
        List<IB> completeIB = ibEntityRepository.findCompleteIB(username,organizationCode);
        List<IB> pendingIB = ibEntityRepository.findPendingIB(username,organizationCode);
        List<IB> revertBackIB = ibEntityRepository.findRevertBackIB(username,organizationCode);
        List<IB> closedIB = ibEntityRepository.findClosedIB(username,organizationCode);

        int openCountIB = openIB.size();
        int inProgressCountIB = inProgressIB.size();
        int completedCountIB = completeIB.size();
        int pendingCountIB = pendingIB.size();
        int revertBackCountIB = revertBackIB.size();
        int closedCountIB = closedIB.size();

        int ibTotalCount = openCountIB+inProgressCountIB+completedCountIB+pendingCountIB+revertBackCountIB+closedCountIB;

        countsList.add(new Dashboard("IB", ibTotalCount, openCountIB,inProgressCountIB,completedCountIB,pendingCountIB,revertBackCountIB,closedCountIB));


        //AIM Module

        List<Aim> openAIM= aimRepository.findOpenAim(username,organizationCode);
        List<Aim> revertBackAIM = aimRepository.findRevertBackAim(username,organizationCode);
        List<Aim> closedAIM = aimRepository.findClosedAim(username,organizationCode);

        int aimOpenCount = openAIM.size();
        int aimRevertBackCount = revertBackAIM.size();
        int aimClosedCount = closedAIM.size();

        int aimTotalCount = aimOpenCount + aimRevertBackCount + aimClosedCount;

        countsList.add(new Dashboard("AIM", aimTotalCount, aimOpenCount,null,null,null,aimRevertBackCount,aimClosedCount));


        //CWF Status

        List<CWF> openCWF= cwfRepository.findOpenCWF(username,organizationCode);
        List<CWF> completeCWF = cwfRepository.findCompleteCWF(username,organizationCode);
        List<CWF> pendingCWF = cwfRepository.findPendingCWF(username,organizationCode);
        List<CWF> revertBackCWF = cwfRepository.findRevertBackCWF(username,organizationCode);
        List<CWF> closedCWF = cwfRepository.findClosedCWF(username,organizationCode);

        int cwfOpenCount = openCWF.size();
        int cwfCompletedCount = completeCWF.size();
        int cwfPendingCount = pendingCWF.size();
        int cwfRevertBackCount = revertBackCWF.size();
        int cwfClosedCount = closedCWF.size();

        int cwfTotalCount = cwfOpenCount+cwfCompletedCount+cwfPendingCount+cwfRevertBackCount+cwfClosedCount;

        countsList.add(new Dashboard("CWF", cwfTotalCount, cwfOpenCount,null,cwfCompletedCount,cwfPendingCount,cwfRevertBackCount,cwfClosedCount));


        //SA Status

        List<SA> openSA= saRepository.findOpenSAs(username,organizationCode);
        List<SA> approvedSA = saRepository.findApprovedSAs(username,organizationCode);
        List<SA> pendingSA = saRepository.findApproverPendingSAs(username,organizationCode);

        int saOpenCount = openSA.size();
        int saPendingCount = approvedSA.size();
        int saApprovedCount = pendingSA.size();

        int saTotalCount = saOpenCount+saPendingCount+saApprovedCount;
        countsList.add(new Dashboard("SA", saTotalCount, saOpenCount,null,saPendingCount,saApprovedCount,null,null));



        return new ResponseModel<>(true, "Success", countsList);
    }


}
