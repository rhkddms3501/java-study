package chapter03;
import chapter03.Song;

public class SongTest {

	public static void main(String[] args) {
//		Song song = new Song();
//		song.setTitle("좋은날");
//		song.setArtist("아이유");
//		song.setAlbum("REAL");
//		song.setComposer("이민수");
//		song.setTrack(3);
//		song.setYear(2010);
		
		Song song1 = new Song("드라마", "아이유", "단편집", "아이유", 1, 2022);
		
		Song song2 = new Song("무릎", "아이유");
		
		
		
//		song.show();
		song1.show();
		song2.show();

	}

}
