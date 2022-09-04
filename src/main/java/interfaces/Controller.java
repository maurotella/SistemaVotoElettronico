//ROCCO
//package interfaces;
//
//public abstract class Controller {
//    public void init() {
//
//    }
//
//    public void onNavigateFrom(Controller sender, Object parameter) {
//
//    }
//
//    public int getTotemId() {
//        return controllers.App.getTotemId();
//    }
//
//    public void setTotemId(int id) {
//        controllers.App.setTotemId(id);
//    }
//
//    public boolean isFirstHomeLoad() {
//        return !controllers.App.homeLoaded;
//    }
//
//    public void navigate(String view, Object parameter) {
//        try {
//            controllers.App.navigate(this, view, parameter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void navigate(String view) {
//        navigate(view, null);
//    }
//}