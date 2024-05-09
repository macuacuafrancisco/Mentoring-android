package mz.org.csaude.mentoring.dto.ronda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import mz.org.csaude.mentoring.base.dto.BaseEntityDTO;
import mz.org.csaude.mentoring.dto.location.HealthFacilityDTO;
import mz.org.csaude.mentoring.model.ronda.Ronda;
import mz.org.csaude.mentoring.model.ronda.RondaMentee;
import mz.org.csaude.mentoring.model.ronda.RondaMentor;
@Data
@NoArgsConstructor
public class RondaDTO extends BaseEntityDTO {
    private String description;
    private Date startDate;
    private Date endDate;
    private RondaTypeDTO rondaType;
    private HealthFacilityDTO healthFacility;
    private List<RondaMenteeDTO> rondaMentees;
    private List<RondaMentorDTO> rondaMentors;
    public RondaDTO(Ronda ronda) {
        super(ronda);
        this.setRondaType(new RondaTypeDTO(ronda.getRondaType()));
        this.setHealthFacility(new HealthFacilityDTO(ronda.getHealthFacility()));
        if(ronda.getRondaMentees()!=null && !ronda.getRondaMentees().isEmpty()) {
            List<RondaMenteeDTO> rondaMenteeDTOS = new ArrayList<>();
            for (RondaMentee rondaMentee : ronda.getRondaMentees()) {
                RondaMenteeDTO rondaMenteeDTO = new RondaMenteeDTO(rondaMentee);
                rondaMenteeDTOS.add(rondaMenteeDTO);
            }
            this.setRondaMentees(rondaMenteeDTOS);
        }
        if(ronda.getRondaMentors()!=null && !ronda.getRondaMentors().isEmpty()) {
            List<RondaMentorDTO> rondaMentorDTOS = new ArrayList<>();
            for (RondaMentor rondaMentor : ronda.getRondaMentors()) {
                RondaMentorDTO RondaMentorDTO = new RondaMentorDTO(rondaMentor);
                rondaMentorDTOS.add(RondaMentorDTO);
            }
            this.setRondaMentors(rondaMentorDTOS);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RondaTypeDTO getRondaType() {
        return rondaType;
    }

    public void setRondaType(RondaTypeDTO rondaType) {
        this.rondaType = rondaType;
    }

    public HealthFacilityDTO getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(HealthFacilityDTO healthFacility) {
        this.healthFacility = healthFacility;
    }

    public List<RondaMenteeDTO> getRondaMentees() {
        return rondaMentees;
    }

    public void setRondaMentees(List<RondaMenteeDTO> rondaMentees) {
        this.rondaMentees = rondaMentees;
    }

    public List<RondaMentorDTO> getRondaMentors() {
        return rondaMentors;
    }

    public void setRondaMentors(List<RondaMentorDTO> rondaMentors) {
        this.rondaMentors = rondaMentors;
    }
    public Ronda getRonda() {
        Ronda ronda = new Ronda();
        ronda.setId(this.getId());
        ronda.setUuid(this.getUuid());
        ronda.setSyncStatus(this.getSyncSatus());
        ronda.setDescription(this.getDescription());
        ronda.setStartDate(this.getStartDate());
        ronda.setEndDate(this.getEndDate());
        if(this.getRondaType()!=null) {
            ronda.setRondaType(this.getRondaType().getRondaType());
        }
        if(this.getHealthFacility()!=null) {
            ronda.setHealthFacility(this.getHealthFacility().getHealthFacilityObj());
        }
        if(this.getRondaMentors()!=null && !this.getRondaMentors().isEmpty()) {
            List<RondaMentor> rondaMentors = new ArrayList<>();
            for (RondaMentorDTO rondaMentorDTO: this.getRondaMentors()) {
                RondaMentor rondaMentor = rondaMentorDTO.getRondaMentor();
                rondaMentors.add(rondaMentor);
            }
            ronda.setRondaMentors(rondaMentors);
        }
        if(this.getRondaMentees()!=null && !this.getRondaMentees().isEmpty()) {
            List<RondaMentee> rondaMentees = new ArrayList<>();
            for (RondaMenteeDTO rondaMenteeDTO: this.getRondaMentees()) {
                RondaMentee rondaMentee = rondaMenteeDTO.getRondaMentee();
                rondaMentees.add(rondaMentee);
            }
            ronda.setRondaMentees(rondaMentees);
        }
        return ronda;
    }
}