public enum accessRights {
    Create,
    Delete,
    CreateDelete;


    public String toString() {
        switch (this){
            case Delete:
                return "01";
            case Create:
                return "10";
            case CreateDelete:
                return "11";
        }
        return null;
    }
}
