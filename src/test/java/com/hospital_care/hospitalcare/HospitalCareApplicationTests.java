package com.hospital_care.hospitalcare;

import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.service.BedService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalCareApplicationTests {

    @Autowired
    BedService bedService;

    @Test
    void contextLoads() {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 9; j++) {
                Bed bed = new Bed();
                bed.setCreateBy("root");
                bed.setFloor(i + "");
                bed.setRoomNo(i + "0" + j);
                bed.setPositionType(0);
                bed.setBedStatus(0);
                for (int k = 1; k <= 4; k++) {
                    bed.setBedNo(k+"");
                    bedService.save(bed);
                }
            }
        }
    }

}
