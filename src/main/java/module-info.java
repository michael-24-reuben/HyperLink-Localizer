module fx.pisces.hyperlocal.hyperlinklocalizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires org.jetbrains.annotations;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.edge_driver;
    requires org.checkerframework.checker.qual;
    requires dev.failsafe.core;

    exports fx.pisces.hyperlocal;
    exports fx.pisces.hyperlocal.assets.event.dragevent;
    exports fx.pisces.hyperlocal.assets.event.dragevent.bookings;
    exports fx.pisces.hyperlocal.assets.graphics;
    exports fx.pisces.hyperlocal.assets.graphics.acn_file_panel;
    exports fx.pisces.hyperlocal.assets.graphics.stp_up_file;
    exports fx.pisces.hyperlocal.assets.image;
    exports fx.pisces.samples.drag;
    exports fx.pisces.samples.launch;
    exports fx.pisces.samples.treeview;

    opens fx.pisces.hyperlocal.assets.image.icons to javafx.fxml;
    opens fx.pisces.hyperlocal to javafx.fxml;
    opens fx.pisces.hyperlocal.assets.graphics to javafx.fxml;
    opens fx.pisces.hyperlocal.assets.graphics.acn_file_panel to javafx.fxml;
    opens fx.pisces.hyperlocal.assets.graphics.stp_up_file to javafx.fxml;
    opens fx.pisces.samples.launch to javafx.fxml;
    opens fx.pisces.hyperlocal.assets.image to javafx.fxml;
    opens fx.pisces.samples.treeview to javafx.fxml;
    opens fx.pisces.samples.drag to javafx.fxml;
}
