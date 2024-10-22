//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterFNDUser;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterFNDUserRepository;
//import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity.FNDUser;
//import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Repository.FNDUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@EnableScheduling
//public class MasterFNDUserService implements CommandLineRunner {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private MasterFNDUserRepository masterFNDUserRepository;
//
//    @Autowired
//    private FNDUserRepository fndUserRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String sql = "SELECT * FROM APPS.XXHIL_EAM_FND_USER_V ";
//
//        List<MasterFNDUser> results = jdbcTemplate.query(sql,
//                BeanPropertyRowMapper.newInstance(MasterFNDUser.class));
//
//        for (MasterFNDUser masterFNDUser : results) {
//            Integer userId = masterFNDUser.getUserId();
//
//            // Check if the work request already exists
//            Optional<MasterFNDUser> optionalMasterFNDUser = masterFNDUserRepository.findByUserId(userId);
//
//            MasterFNDUser fndUser;
//            if (optionalMasterFNDUser.isPresent()) {
//                fndUser = optionalMasterFNDUser.get();
//                fndUser.setUSER_NAME(masterFNDUser.getUSER_NAME());
//                fndUser.setDESCRIPTION(masterFNDUser.getDESCRIPTION());
//                fndUser.setEMAIL_ADDRESS(masterFNDUser.getEMAIL_ADDRESS());
//                fndUser.setEND_DATE(masterFNDUser.getEND_DATE());
//            } else {
//                fndUser = new MasterFNDUser();
//                fndUser.setUserId(masterFNDUser.getUserId());
//                fndUser.setUSER_NAME(masterFNDUser.getUSER_NAME());
//                fndUser.setDESCRIPTION(masterFNDUser.getDESCRIPTION());
//                fndUser.setEMAIL_ADDRESS(masterFNDUser.getEMAIL_ADDRESS());
//                fndUser.setEND_DATE(masterFNDUser.getEND_DATE());
//            }
//            masterFNDUserRepository.save(fndUser);
//        }
//    }
//
////    @Scheduled(cron = "0 */2 * * * *") // Run every 2 minutes
////    public void addFndUserDataAutomatically() {
////        List<MasterFNDUser> masterAssetList = masterFNDUserRepository.findAll();
////
////        for (MasterFNDUser assets : masterAssetList) {
////            Optional<FNDUser> assetOptional = fndUserRepository.findByUserId(assets.getUserId());
////
////            FNDUser fndUser;
////            if (assetOptional.isPresent()) {
////                fndUser = assetOptional.get();
////                fndUser.setUserName(assets.getUSER_NAME());
////                fndUser.setDESCRIPTION(assets.getDESCRIPTION());
////                fndUser.setEMAIL_ADDRESS(assets.getEMAIL_ADDRESS());
////                fndUser.setEND_DATE(assets.getEND_DATE());
////
////                System.out.println("Updated FND User " + assets.getUserId());
////            } else {
////                fndUser = new FNDUser();
////                fndUser.setUserId(assets.getUserId());
////                fndUser.setUserName(assets.getUSER_NAME());
////                fndUser.setDESCRIPTION(assets.getDESCRIPTION());
////                fndUser.setEMAIL_ADDRESS(assets.getEMAIL_ADDRESS());
////                fndUser.setEND_DATE(assets.getEND_DATE());
////                System.out.println("Inserted FND User " + assets.getUserId());
////            }
////
////            fndUserRepository.save(fndUser);
////        }
////    }
//
//}
