package org.example.bank;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Serialisierer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        //Fügt erstmal den Klassennamen als Property ein
        JsonObject jason = new JsonObject();
        jason.addProperty("CLASSNAME", transaction.getClass().getSimpleName());

        //Fügt in das bisherige JsonObject ein neues JsonObject ein, um die gewünschte verschachtelte Struktur zu erreichen
        //Dieses neue JsonObject hat alle objektspezifischen Klassenattribute
        JsonObject jamie = new JsonObject();
        if(transaction instanceof Payment){
            Payment p = (Payment) transaction;
            jamie.addProperty("incomingInterest", p.getIncomingInterest());
            jamie.addProperty("outgoingInterest", p.getOutgoingInterest());
        }
        else if (transaction instanceof Transfer) {
            Transfer t = (Transfer) transaction;
            jamie.addProperty("sender", t.getSender());
            jamie.addProperty("recipient", t.getRecipient());
        }
        jamie.addProperty("date", transaction.getDate());
        jamie.addProperty("amount", transaction.getAmount());
        jamie.addProperty("description", transaction.getDescription());
        jason.add("INSTANCE", jamie);
        return jason;
    }

    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //Deserialisiert das äußere JsonObject und das innere Objekt
        JsonObject jason = jsonElement.getAsJsonObject();
        JsonElement instance = jason.get("INSTANCE");
        JsonObject jamie = instance.getAsJsonObject();

        //Erstellt der CLASSNAME Property entsprechende Objekte der Unterklassen und übernimmt die ausgelesenen Attribute
        if (jason.get("CLASSNAME").getAsString().contains("Payment")){
            return new Payment(
                    jamie.get("date").getAsString(),
                    jamie.get("amount").getAsDouble(),
                    jamie.get("description").getAsString(),
                    jamie.get("incomingInterest").getAsDouble(),
                    jamie.get("outgoingInterest").getAsDouble()
            );
        }
        else if (jason.get("CLASSNAME").getAsString().contains("OutgoingTransfer")){
            return new OutgoingTransfer(
                    jamie.get("date").getAsString(),
                    jamie.get("amount").getAsDouble(),
                    jamie.get("description").getAsString(),
                    jamie.get("sender").getAsString(),
                    jamie.get("recipient").getAsString()
            );
        }
        else if (jason.get("CLASSNAME").getAsString().contains("IncomingTransfer")){
            return new IncomingTransfer(
                    jamie.get("date").getAsString(),
                    jamie.get("amount").getAsDouble(),
                    jamie.get("description").getAsString(),
                    jamie.get("sender").getAsString(),
                    jamie.get("recipient").getAsString()
            );
        }
        return null;
    }
}
