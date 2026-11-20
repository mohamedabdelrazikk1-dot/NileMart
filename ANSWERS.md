# ANSWERS

## Checkpoints

### Checkpoint 1
1. Which class contains new StandardPricingService()? Which class would you edit to switch to promotional pricing?

AppConfig creates the StandardPricingService object. To switch to promotional pricing, I would change the configuration in AppConfig to use PromotionalPricingService.

2. Did you have to change ProductService to swap the pricing? Why not?

No, I didn't have to change ProductService because it depends on the PricingService interface, not a specific implementation. Both pricing classes implement the same interface, so I can replace one with the other without changing ProductService.

3. Now imagine 60 classes like this. Who ends up owning all the new calls? (That is the problem Spring solves.)

Without Spring, the application would need a central class to create objects and manage their dependencies. As the application grows, this class would become responsible for many object creation calls and dependency relationships. Spring handles this through its IoC container and Dependency Injection.

4. Product.equals uses only the SKU. What breaks inside a HashMap if you change the SKU of a product after you put it in as a key?

Changing the SKU changes the product's hash code. The HashMap may look for the key in a different bucket than the one where it was originally stored. As a result, get() or remove() may fail to find the product, even though the key is still inside the map.

### Checkpoint 2

**1. Which classes appear in the list of bean names? Which do not, and why?**

In my project, I found that classes annotated with `@Component`, `@Service`, and `@Configuration` appear in the bean names list. Classes like `Product`, `Customer`, and `Order` do not appear because I use them as normal domain objects, not as Spring beans.

**2. Remove `@Primary` and run again. Which two candidates does the exception name?**

When I remove `@Primary`, Spring finds two candidates: `StandardPricingService` and `PromotionalPricingService`. Both implement `PricingService`, so Spring cannot decide which one to inject.

**3. When `@ComponentScan` finds `ProductService`, does it create the object at that moment?**

No. When Spring scans and finds `ProductService`, it registers a bean definition first. The actual object is created later by the Spring container, usually when the application context starts for singleton beans.

### Checkpoint 3

**1. ShippingQuoteService asks for two ShippingCalculators. Show which rule gives each one its bean (`@Primary` vs `@Qualifier`).**

In my project, I use `@Primary` on `ZoneShippingCalculator`, so Spring injects it when I ask for a `ShippingCalculator` without specifying a name.

For `FreeShippingCalculator`, I use `@Component("freeShipping")` and `@Qualifier("freeShipping")` in the constructor to tell Spring exactly which bean I need.

**2. What would happen if you added a third calculator and forgot to mark it? Try it.**

If I add a third `ShippingCalculator` without `@Primary` or `@Qualifier`, Spring will still use `ZoneShippingCalculator` when injecting by type because it is marked with `@Primary`. However, if I remove `@Primary`, Spring will find multiple candidates and throw `NoUniqueBeanDefinitionException` when it needs to choose one without a qualifier.

**3. Write the one-line test: `quote(CAIRO, 5000.00) = 0.00` but `quote(MATROUH, 999999.00) = 120.00`.**

I can test both cases using `compareTo()` because `BigDecimal.equals()` also checks the scale.


assertAll(
    () -> assertEquals(0, service.quote(Governorate.CAIRO, new BigDecimal("5000.00")).compareTo(new BigDecimal("0.00"))),
    () -> assertEquals(0, service.quote(Governorate.MATROUH, new BigDecimal("999999.00")).compareTo(new BigDecimal("120.00")))

)
**4. Add a PushNotificationSender that prints `[PUSH]` to .... Which existing files did you have to edit?**

I did not need to edit any existing files. I only added a new class called `PushNotificationSender` that implements `NotificationSender` and is annotated with `@Component`.

Spring discovers it automatically through component scanning, and the existing notification service can use it through the `NotificationSender` interface.

### Checkpoint 4

**1. `getBean(ProductService.class) == getBean(ProductService.class)` prints true, but the same line for ShoppingCart prints false. Why?**

In my project, `ProductService` is a singleton bean, so Spring returns the same object every time I request it.

`ShoppingCart` is a prototype bean, so Spring creates a new object every time I call `getBean()`.

**2. Print a line inside the repository's `@PostConstruct`. Does it print once, or once per `getBean`? Why?**

It prints once because my repository is a singleton bean. Spring calls `@PostConstruct` after creating the bean, not every time I request it.

**3. What goes wrong if a singleton service keeps a ShoppingCart in a constructor parameter? Who asks the container for each new cart?**

If I inject a prototype `ShoppingCart` directly into a singleton service, Spring creates only one cart when it creates that service. The service keeps using the same cart instead of getting a new one for every customer.

I need to use `ObjectProvider<ShoppingCart>` and call `getObject()` whenever I need a new cart. The `CartRegistry` can request each new cart from the container.

**4. Why `getOrDefault(sku, 0)` and not `get(sku)`?**

I use `getOrDefault(sku, 0)` because the SKU may not exist in the cart yet. It returns zero as the default quantity.

If I use `get(sku)`, it returns `null` for a new SKU, and adding a quantity to `null` can cause a `NullPointerException`.

**5. Why a `LinkedHashMap` and not a `HashMap` for the lines?**

I use `LinkedHashMap` because it keeps the insertion order of products. This makes displaying cart items more predictable.

### Checkpoint 6

**1. Print `context.getBeanDefinitionNames()` again. Find your five controllers, CartRegistry and the three beans of WebConfig. Is StoreConsole in the list? Why not?**

I can find my five controllers and `CartRegistry` in the bean names list because Spring registers them as beans.

I can also find the three beans defined in `WebConfig`: `templateResolver`, `templateEngine`, and `viewResolver`.

`StoreConsole` is not in the list because I did not register it as a Spring bean using `@Component` or `@Bean`.

**2. A controller is a singleton and a cart is a prototype. Show the line where a new cart is requested for each customer, and who asks the container for it.**

I use `objectProvider.getObject()` to request a new cart whenever I need one.

`CartRegistry` asks the Spring container for a new `ShoppingCart` for each customer.

**3. Create a cart in the console, then open `/carts` in the browser. Why does the browser see it? What would change if StoreConsole created its own new `CartRegistry()`?**

The browser can see the cart because both the console and the web application use the same Spring-managed `CartRegistry` singleton.

If `StoreConsole` creates its own `new CartRegistry()`, it will have a separate registry with different cart data, so the browser will not see the cart created by the console.

**4. Why does POST `/carts` return `redirect:/carts/N` and not the view name `carts/view`? What does F5 do in each case?**

I use `redirect:/carts/N` to follow the Post/Redirect/Get pattern.

After the POST request, the browser sends a new GET request to display the cart.

If I refresh the page with F5 after a redirect, the browser repeats the GET request. If I return the view directly, refreshing may submit the POST request again and create the same cart more than once.

**5. Two tabs press "Place order" on the same cart at the same moment. Which keyword decides who wins, and what does the loser see on screen?**

I use the `synchronized` keyword on the `placeOrder()` method to prevent two threads from checking out the same cart at the same time.

The first request that enters the method completes the order. The second request waits, then sees that the cart has already been checked out and receives an error message instead of creating another order.

**6. Remove `@Controller` from CartController and restart. What do you see in the browser, and why?**

When I remove `@Controller`, Spring no longer registers `CartController` as a controller bean.

The browser cannot reach its endpoints, so I get a 404 Not Found error because Spring does not find a matching handler for those requests.

## Principles

- **Encapsulation:** `Product` and `ShoppingCart` use private fields; `ShoppingCart` exposes an unmodifiable `lines()` view, has no quantity setter, and can be opened only once.
- **Abstraction and Interfaces:** `Repository`, `PricingService`, `ShippingCalculator`, and `NotificationSender` expose contracts so callers depend on interfaces rather than implementations.
- **Polymorphism:** `NotificationSender` implementations are used through `List<NotificationSender>`, while `StoreException.status()` provides polymorphic behavior.
- **Inheritance:** `AbstractNotificationSender` and the `StoreException` hierarchy contain shared behavior in parent classes, with subclasses providing only their differences; `send()` is final.
- **Composition over Inheritance:** `CheckoutService` uses five collaborators through composition and does not extend another class.
- **SOLID:** `TaxCalculator` has a single responsibility; new pricing calculators and notification senders can be added without modifying existing classes; shipping implementations are interchangeable; interfaces are small; and dependencies are injected through constructors.
- **Generics:** `Repository<T, ID>` provides a generic repository contract, while `Check.found()` provides a reusable generic helper.
- **Collections:** `ConcurrentHashMap`, `LinkedHashMap`, `EnumMap`, `EnumSet`, and `CopyOnWriteArrayList` are selected according to concurrency, ordering, and enum-related requirements.
- **Streams and Lambdas:** `subtotal`, `groupByCategory`, `search(Predicate)`, and `revenue` use streams and lambdas instead of loops dedicated to filtering or aggregation.
- **Optional:** `findBySku` and `findByNumber` return `Optional` rather than null, and callers use `orElseThrow()` or `Check.found()` instead of `get()`.
- **Exceptions:** The `StoreException` hierarchy represents unchecked business errors, while CSV loading catches and wraps `IOException` with its original cause.
- **Immutability and Records:** `Customer`, `Order`, `OrderLine`, `Quote`, `CartView`, and `LineView` use immutable data structures, defensive list copies, and validation.
- **Enums:** `Zone`, `Governorate`, and `Category` represent domain concepts using enums instead of string constants.
- **equals, hashCode, and toString:** `Product` defines equality and hashing based only on SKU, verified using a `HashSet` in Stage 1.
- **Numbers and BigDecimal:** `Money` and `ShippingQuoteService` use `BigDecimal` and `compareTo()` for monetary comparisons without converting to `double`.
- **Static and Final:** `Money`, `Check`, and `CartView` provide utility functionality with appropriate static and final members and private constructors.
- **Default Methods:** `ProductRepository.findBySku()` and `NotificationSender.supports()` provide shared behavior through interface default methods.
- **Concurrency:** `CartRegistry`, `OrderRegistry`, and `CheckoutService.placeOrder()` use concurrent collections, `AtomicInteger`, and synchronization where necessary.
- **I/O and Try-with-resources:** `InMemoryProductRepository` and `Main` use try-with-resources to close file readers and the web server safely.
- **Date and Time:** `OrderRegistry` uses `LocalDate.now(clock)` and `DateTimeFormatter.BASIC_ISO_DATE` for deterministic date handling.
- **Strings and Formatting:** `Money.format()` and order-number generation use `String.format()` with `Locale.US` and the required formatting patterns.