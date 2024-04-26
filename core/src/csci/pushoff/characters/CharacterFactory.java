package csci.pushoff.characters;

public class CharacterFactory {
    public static Character makeCharacter(int index, float x,float y){
        switch(index) {
            case 1:
                return new Baby(x,y);
            case 2:
                return new captaincash(x,y);
            case 3:
                return new wheelchairKid(x,y);
            default:
                return  new FatMan(x,y);
        }
    }
}
