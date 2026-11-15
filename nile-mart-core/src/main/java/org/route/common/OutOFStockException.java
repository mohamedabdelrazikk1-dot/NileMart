package org.route.common;

public class OutOFStockException extends StoreException {
  public OutOFStockException(String message) {
      super(message);
  }

    @Override
    public int statusCode() {
        return 409;
    }
}
