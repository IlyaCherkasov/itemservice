import static spark.Spark.*;

import com.google.gson.*;
import dao.ItemDAO;
import dto.ItemDTO;
import service.ItemService;
import service.SessionFactoryService;
import service.MessagingService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static MessagingService messagingService = new MessagingService();
	private static ItemService itemService = new ItemService(
        new ItemDAO(new SessionFactoryService()),
        messagingService
        );
    private static GsonBuilder builder = new GsonBuilder();
	private static Gson gson = builder.create();

    public static void main(String[] args) {
        messagingService.setupListener(itemService);
        port(1824);

        // get items
		get("/api/warehouse/items", (req, res) -> itemService.getItems());

        // get item by id
        get("/api/warehouse/items/:itemId", (req, res) -> {
                    try {
                        return itemService.getItemDTOById(Long.parseLong(req.params("itemId")));
                    } catch (Throwable e) {
                        logger.error(e.getMessage());
                        return gson.toJson(e.getMessage());
                    }
                }
        );

        // create new item
		post("/api/warehouse/items", (req, res) ->
			itemService.createItem(new Gson().fromJson(req.body(), ItemDTO.class))
		);

        // add existing items
        put("api/warehouse/items/:itemId/addition/:amount", (req, res) -> {
            try {
                return itemService.addExistingItems(Long.parseLong(req.params("itemId")), Long.parseLong(req.params("amount")));
            } catch (Throwable e) {
                logger.error(e.getMessage());
                return gson.toJson(e.getMessage());
            }
        }
        );

        // change amount of items
		post("api/warehouse/items/:itemId/change/:amount", (req, res) ->{
            try {
                return itemService.changeItemAmount(Long.parseLong(req.params("itemId")), Long.parseLong(req.params("amount")), null);
            } catch (Throwable e) {
                logger.error(e.getMessage());
                return gson.toJson(e.getMessage());
            }
        }
        );

        // reserve items
        post("api/warehouse/items/:itemId/reserve/:amount", (req, res) -> {
            try {
                return itemService.reserveItems(Long.parseLong(req.params("itemId")), Long.parseLong(req.params("amount")), null);
            } catch (Throwable e) {
                logger.error(e.getMessage());
                return gson.toJson(e.getMessage());
            }
        }
        );

        // release items
        post("api/warehouse/items/:itemId/release/:amount", (req, res) -> {
            try {
                return itemService.releaseItems(Long.parseLong(req.params("itemId")), Long.parseLong(req.params("amount")), null);
            } catch (Throwable e) {
                logger.error(e.getMessage());
                return gson.toJson(e.getMessage());
            }
        }
        );
    }

	private static Long parseLong(String s) throws Exception {
		if (s == null || s.equals("") || s.toLowerCase().equals("null")) {
			throw new Exception("Value is empty or null");
		}
		return Long.parseLong(s);
	}
}
