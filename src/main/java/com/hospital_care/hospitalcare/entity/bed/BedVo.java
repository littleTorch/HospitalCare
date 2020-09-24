package com.hospital_care.hospitalcare.entity.bed;

import com.hospital_care.hospitalcare.entity.Bed;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class BedVo {
    private String value;
    private String label;
    private List<BedVo> children;
    private Bed bed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BedVo)) return false;
        BedVo bedVo = (BedVo) o;
        return Objects.equals(value, bedVo.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
