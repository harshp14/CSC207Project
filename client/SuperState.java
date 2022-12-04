package client;

public interface SuperState {
    /**
     * Determines what the message represents and runs a specific method depending on the message
     *
     * @param message message sent from the server
     */
    public void listening(String message);


}
