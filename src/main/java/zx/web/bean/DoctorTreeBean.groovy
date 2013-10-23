package zx.web.bean

import zx.domain.User

class DoctorTreeBean {
    String n
    String d
    List<DoctorTreeBean> e = []

    DoctorTreeBean(User doctor) {
        n = doctor.username
        d = doctor.displayName
        doctor.employees.each { e << new DoctorTreeBean(it) }
    }
}
