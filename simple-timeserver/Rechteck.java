// Diese Klasse hat nichts mit der Client/Server-App zu tun, sondern dient lediglich als Demonstration fuer eine Klasse.
class Rechteck
{
    private int breite;
    private int laenge;
    
    Rechteck(int breite, int laenge)
    {
        this.breite = breite;
        this.laenge = laenge;
    }
    
    void vervierfacheDich() {
        this.breite = this.breite * 2;
        this.laenge = this.laenge * 2;
    }
    
    int berechneFlaecheninhalt() {
        return this.breite * this.laenge;
    }
    
    int berechneUmfang() {
        return 2 * this.breite + 2 * this.laenge;
    }
}
