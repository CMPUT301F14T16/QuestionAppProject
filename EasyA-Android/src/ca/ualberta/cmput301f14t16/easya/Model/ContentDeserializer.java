package ca.ualberta.cmput301f14t16.easya.Model;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ContentDeserializer implements JsonSerializer<Content>,
		JsonDeserializer<Content> {
	private static final String CLASS_META_KEY = "CLASS_META_KEY";

	/**
	 * Deserializes the given json element as the proper {@link Content}
	 * subclass.
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Content deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		JsonObject jsonObj = jsonElement.getAsJsonObject();
		String className = jsonObj.get(CLASS_META_KEY).getAsString();
		try {
			Class<?> clz = Class.forName(className);
			return jsonDeserializationContext.deserialize(jsonElement, clz);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e);
		}
	}

	/**
	 * Serializes the given object, adding a CLASS_META_KEY property.
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 *      java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Content object, Type type,
			JsonSerializationContext jsonSerializationContext) {
		JsonElement jsonEle = jsonSerializationContext.serialize(object,
				object.getClass());
		jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY,
				object.getClass().getCanonicalName());
		return jsonEle;
	}
}
