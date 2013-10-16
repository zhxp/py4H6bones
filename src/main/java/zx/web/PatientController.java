package zx.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zx.domain.Patient;
import zx.domain.PatientType;
import zx.domain.Plan;
import zx.domain.User;
import zx.exception.ExistedException;
import zx.service.PatientService;
import zx.service.PlanService;
import zx.service.UserService;
import zx.web.config.Login;
import zx.web.bean.PatientInfoBean;
import zx.web.bean.PlanBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlanService planService;

    @RequestMapping("create")
    @ResponseBody
    public Map<String, Object> createPatient(String pid, String phone) {
        Map<String, Object> result = new HashMap<>();
        List<String> errors = new LinkedList<>();
        result.put("errors", errors);
        if (StringUtils.isBlank(pid)) {
            result.put("pid", "病人标识不能为空");
            errors.add("pid");
        } else {
            pid = pid.trim();
        }
        if (StringUtils.isBlank(phone)) {
            result.put("phone", "电话号码不能为空");
            errors.add("phone");
        } else {
            phone = phone.trim();
        }
        if (errors.isEmpty()) {
            try {
                Patient patient = patientService.createPatient(pid);
                patient.setPhone(phone);
                patientService.save(patient);
            } catch (ExistedException e) {
                result.put("pid", "病人标识已存在");
                errors.add("pid");
            } catch (Exception e) {
                result.put("_serverError", e.getMessage());
                errors.add("_serverError");
            }
        }
        return result;
    }

    @RequestMapping("findNew")
    @ResponseBody
    public List<Map<String, Object>> findNewPatients(@Login User user) {
        List<Patient> patients = patientService.findNewPatientsByDoctor(user);
        List<Map<String, Object>> result = new ArrayList<>(patients.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Patient patient : patients) {
            Map<String, Object> o = new HashMap<>();
            result.add(o);
            o.put("pid", patient.getPid());
            o.put("phone", patient.getPhone());
            o.put("createdAt", dateFormat.format(patient.getCreatedAt()));
        }
        return result;
    }

    @RequestMapping("findRegistered")
    @ResponseBody
    public List<Map<String, Object>> findRegisteredPatients(@Login User user) {
        List<Patient> patients = patientService.findRegisteredPatientsByDoctor(user);
        List<Map<String, Object>> result = new ArrayList<>(patients.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Patient patient : patients) {
            Map<String, Object> o = new HashMap<>();
            result.add(o);
            o.put("pid", patient.getPid());
            o.put("name", patient.getName());
            o.put("phone", patient.getPhone());
            o.put("createdAt", dateFormat.format(patient.getCreatedAt()));
        }
        return result;
    }

    @RequestMapping("findPlanned")
    @ResponseBody
    public List<Map<String, Object>> findPlannedPatients(@Login User user) {
        List<Patient> patients = patientService.findPlannedPatientsByDoctor(user);
        List<Map<String, Object>> result = new ArrayList<>(patients.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Patient patient : patients) {
            Map<String, Object> o = new HashMap<>();
            result.add(o);
            o.put("pid", patient.getPid());
            o.put("name", patient.getName());
            o.put("phone", patient.getPhone());
            o.put("createdAt", dateFormat.format(patient.getCreatedAt()));
        }
        return result;
    }

    @RequestMapping("details")
    @ResponseBody
    public PatientInfoBean getPatientInfo(String pid) {
        PatientInfoBean info = new PatientInfoBean();
        Patient patient = patientService.findByPid(pid);
        if (patient != null) {
            info.assignFrom(patient);
        }
        return info;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> editPatientInfo(@RequestBody PatientInfoBean patientInfoBean) {
        Map<String, Object> result = new HashMap<>();
        List<String> errors = new LinkedList<>();
        Patient patient = patientService.findByPid(patientInfoBean.getPid());
        patientInfoBean.copyTo(patient, patientService, userService);
        patientService.save(patient);
        return result;
    }

    @RequestMapping("createPlan/{pid}")
    @ResponseBody
    public List<String> createPlan(@PathVariable String pid, @RequestBody List<PlanBean> planBeans) {
        List<String> errors = new LinkedList<>();
        Patient patient = patientService.findByPid(pid);
        if (patient.getPatientType() != PatientType.REGISTERED && patient.getPatientType() != PatientType.PLANNED) {
            errors.add("不能修改当前病人计划，当前病人状态为：" + patient.getPatientType().getViewText());
        } else {
            List<Plan> plans = new ArrayList<>(planBeans.size());
            for (PlanBean planBean : planBeans) {
                Plan plan = new Plan();
                planBean.copyTo(plan);
                plans.add(plan);
            }
            planService.createPlan(patient, plans);
        }
        return errors;
    }

    @RequestMapping("plans/{pid}")
    @ResponseBody
    public List<PlanBean> findPlans(@PathVariable String pid) {
        List<Plan> plans = planService.findByPid(pid);
        List<PlanBean> planBeans = new ArrayList<>(plans.size());
        for (Plan plan : plans) {
            planBeans.add(new PlanBean(plan));
        }
        return planBeans;
    }
}
