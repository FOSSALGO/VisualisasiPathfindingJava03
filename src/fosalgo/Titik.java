package fosalgo;

public class Titik {
    int x,y;

    public Titik(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }
    
    public boolean compare(Titik titikLain){
        boolean hasil = false;
        if(titikLain.x==x && titikLain.y==y){
            hasil = true;
        }
        return hasil;
    }
    
}
