package zx.domain;

public enum PatientType {
    NEW("已加入"),
    REGISTERED("已登录"),
    PLANNED("已计划"),
    TRAINED("已训练"),
    FINISHED("已完成"),
    ;

    PatientType(String viewText) {
        this.viewText = viewText;
    }

    private String viewText;

    public String getViewText() {
        return viewText;
    }
}
