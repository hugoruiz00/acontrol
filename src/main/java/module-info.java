module com.hugoruiz.acontrol {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    
    opens com.hugoruiz.acontrol.controller to javafx.fxml;
    opens com.hugoruiz.acontrol.model to org.hibernate.orm.core, javafx.base;
    exports com.hugoruiz.acontrol;
}
