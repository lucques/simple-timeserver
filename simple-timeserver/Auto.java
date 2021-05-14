class Auto
{
    private int idNr;
    private String marke;
    private int kilometerstand;
    
    Auto(int idNr, String marke) {
        this.idNr = idNr;
        this.marke = marke;
        this.kilometerstand = 0;
    }
    
    void erhoeheKilometerstand(int gefahreneStrecke) {
        this.kilometerstand = this.kilometerstand + gefahreneStrecke;
    }
    
    int berechneMietpreisProTag() {
        return 50 - 5*this.kilometerstand/40000;
    }
}
