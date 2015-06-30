package stomt4j.entities;

public class Images {
	
	
	private Image avatar;
	private Image cover;
	private Image stomt;
	
	"images":{"avatar":{"url":"https://test.rest.stomt.com/placeholders/30/5.png","w":30,"h":42}},
	
	
	
	private class Image {
		
		String context;
		String name;
		String data;
		String url;
		int width;
		int height;
		
	}

}
