# Spring Bean Lifecycle & Post Processors

A Spring Core project demonstrating the **Spring Bean Lifecycle**, `BeanFactoryPostProcessor`, `BeanPostProcessor`, and different initialization and destruction callback mechanisms using Java-based configuration.

## 📌 Overview

This project explores what happens to a Spring bean from the moment its **BeanDefinition** is processed until the bean is destroyed.

The project demonstrates three important areas of Spring's container:

```text
BeanFactoryPostProcessor
        ↓
Bean Definition Processing
        ↓
Bean Creation
        ↓
Dependency Injection
        ↓
BeanPostProcessor
        ↓
Initialization Callbacks
        ↓
Bean Ready
        ↓
Application Running
        ↓
Destruction Callbacks
```

## 🎯 Objective

The main objective is to understand:

* How Spring creates and manages beans
* How BeanFactoryPostProcessor works with BeanDefinitions
* How BeanPostProcessor works with bean instances
* How initialization callbacks work
* How destruction callbacks work
* The difference between various lifecycle mechanisms
* The role of the Spring IoC container in bean management

## 🏗️ Project Structure

```text
src
└── main
    └── java
        └── org.employee
            │
            ├── app
            │   └── Main.java
            │
            ├── config
            │   └── AppConfig.java
            │
            ├── employee
            │   ├── Employee.java
            │   └── EmployeeService.java
            │
            └── processor
                ├── EmployeeBeanFactoryPostProcessor.java
                └── EmployeeBeanPostProcessor.java
```

## 🔧 Technologies Used

* Java 21
* Spring Framework 6.2.8
* Spring Core
* Spring Context
* Maven
* IntelliJ IDEA

---

# 🔄 Spring Bean Lifecycle

The project demonstrates the major stages of a Spring bean lifecycle:

```text
BeanDefinition
      ↓
BeanFactoryPostProcessor
      ↓
Bean Instantiation
      ↓
Dependency Injection
      ↓
BeanPostProcessor - Before Initialization
      ↓
@PostConstruct
      ↓
InitializingBean
      ↓
initMethod
      ↓
BeanPostProcessor - After Initialization
      ↓
Bean Ready
      ↓
Application Running
      ↓
ApplicationContext.close()
      ↓
@PreDestroy
      ↓
DisposableBean
      ↓
destroyMethod
      ↓
Bean Destroyed
```

The exact ordering can vary when multiple infrastructure post-processors are involved, but this project is designed to demonstrate the major lifecycle stages and their relationships.

---

# 1. BeanFactoryPostProcessor

`BeanFactoryPostProcessor` works with the **BeanDefinition**, before Spring creates the actual bean instance.

In this project, the processor modifies the employee's department:

```text
Original BeanDefinition
        ↓
department = Engineering
        ↓
BeanFactoryPostProcessor
        ↓
department = Platform Engineering
        ↓
Employee Bean Creation
```

Example:

```java
public class EmployeeBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) {

        BeanDefinition definition =
                beanFactory.getBeanDefinition("employee");

        definition.getPropertyValues()
                .add("department", "Platform Engineering");
    }
}
```

### Key Concept

```text
BeanFactoryPostProcessor
        ↓
Works with BeanDefinition
        ↓
Before bean instance creation
```

---

# 2. BeanPostProcessor

`BeanPostProcessor` works with the **actual bean instance** during the initialization phase.

It provides two important methods:

```java
postProcessBeforeInitialization()
```

and:

```java
postProcessAfterInitialization()
```

Example:

```java
public class EmployeeBeanPostProcessor
        implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
            Object bean,
            String beanName) {

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
            Object bean,
            String beanName) {

        return bean;
    }
}
```

### Key Concept

```text
BeanPostProcessor
        ↓
Works with actual bean instance
        ↓
Around initialization
```

A `BeanPostProcessor` can return:

```text
Original Bean
     ↓
Modified Bean
```

or potentially:

```text
Original Bean
     ↓
Wrapper / Proxy
```

This concept becomes especially important when learning **Spring AOP**.

---

# 3. Initialization Callbacks

This project demonstrates three different initialization mechanisms.

## `@PostConstruct`

```java
@PostConstruct
public void postConstruct() {
    System.out.println("@PostConstruct executed");
}
```

Used for bean initialization after dependency injection.

---

## `InitializingBean`

```java
@Override
public void afterPropertiesSet() {
    System.out.println(
        "InitializingBean executed"
    );
}
```

Spring calls `afterPropertiesSet()` as an initialization callback.

---

## `initMethod`

Configured in Java configuration:

```java
@Bean(initMethod = "customInit")
public Employee employee() {
    return new Employee();
}
```

The corresponding method:

```java
public void customInit() {
    System.out.println(
        "Custom initMethod executed"
    );
}
```

---

# 4. Destruction Callbacks

The project also demonstrates three destruction mechanisms.

## `@PreDestroy`

```java
@PreDestroy
public void preDestroy() {
    System.out.println("@PreDestroy executed");
}
```

Called during bean destruction.

---

## `DisposableBean`

```java
@Override
public void destroy() {
    System.out.println(
        "DisposableBean.destroy() executed"
    );
}
```

Spring calls this method during destruction.

---

## `destroyMethod`

Configured in Java configuration:

```java
@Bean(
    initMethod = "customInit",
    destroyMethod = "customDestroy"
)
public Employee employee() {
    return new Employee();
}
```

Corresponding method:

```java
public void customDestroy() {
    System.out.println(
        "Custom destroyMethod executed"
    );
}
```

---

# 🧠 Important Comparison

| Mechanism                  | Works With     | Main Purpose                              |
| -------------------------- | -------------- | ----------------------------------------- |
| `BeanFactoryPostProcessor` | BeanDefinition | Modify bean metadata/configuration        |
| `BeanPostProcessor`        | Bean instance  | Process beans during initialization       |
| `@PostConstruct`           | Bean           | Initialization callback                   |
| `InitializingBean`         | Bean           | Initialization callback through interface |
| `initMethod`               | Bean           | Configuration-based initialization        |
| `@PreDestroy`              | Bean           | Destruction callback                      |
| `DisposableBean`           | Bean           | Destruction callback through interface    |
| `destroyMethod`            | Bean           | Configuration-based destruction           |

---

# 🔑 BeanFactoryPostProcessor vs BeanPostProcessor

This is one of the most important concepts in the project.

### BeanFactoryPostProcessor

```text
BeanDefinition
      ↓
BeanFactoryPostProcessor
      ↓
Bean Creation
```

It works **before the bean instance exists**.

### BeanPostProcessor

```text
Bean Instance
      ↓
Before Initialization
      ↓
Initialization
      ↓
After Initialization
```

It works with the **actual bean instance**.

### Easy way to remember

```text
BeanFactoryPostProcessor
→ "Change how the bean should be created."

BeanPostProcessor
→ "Process the bean that Spring created."
```

---

# 📋 Example Employee Flow

The `Employee` bean contains:

```text
Constructor
    ↓
Properties injected
    ↓
@PostConstruct
    ↓
InitializingBean
    ↓
initMethod
    ↓
Bean ready
```

When the application shuts down:

```text
ApplicationContext.close()
        ↓
@PreDestroy
        ↓
DisposableBean
        ↓
destroyMethod
```

---

# ⚠️ Important Learning Note

This project intentionally demonstrates multiple lifecycle mechanisms together for learning.

In a real application, you generally **do not need to use all of these mechanisms on the same bean**.

The purpose here is to understand the differences between:

```text
Annotation-based callbacks
Interface-based callbacks
Configuration-based callbacks
Container extension points
```

---

# 🧪 Practical Experiment

After running the project successfully, try removing one mechanism at a time.

For example:

```text
Remove @PostConstruct
→ Observe what changes

Remove InitializingBean
→ Observe what changes

Remove initMethod
→ Observe what changes

Remove BeanPostProcessor
→ Observe what changes

Remove BeanFactoryPostProcessor
→ Observe what changes
```

This is more useful than simply memorizing the lifecycle diagram.

---

# 💡 Key Learnings

* Spring manages the complete lifecycle of its beans.
* BeanFactoryPostProcessor operates on BeanDefinitions.
* BeanPostProcessor operates on bean instances.
* `@PostConstruct` is an initialization callback.
* `InitializingBean` provides an interface-based initialization callback.
* `initMethod` provides configuration-based initialization.
* `@PreDestroy` is a destruction callback.
* `DisposableBean` provides an interface-based destruction callback.
* `destroyMethod` provides configuration-based destruction.
* BeanPostProcessor can potentially wrap or replace a bean.
* `ApplicationContext.close()` is important for observing destruction callbacks in standalone applications.

---

# ▶️ How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure Java 21.
4. Load Maven dependencies.
5. Run `Main.java`.
6. Observe the bean processing and lifecycle messages.
7. Close the Spring context.
8. Observe the destruction callbacks.

---

# 📈 Learning Progress

```text
01 Manual Dependency Injection          ✅
02 Spring XML Configuration             ✅
03 Annotation Configuration             ✅
04 Java-Based Configuration             ✅
05 Bean Scopes                           ✅
06 Bean Lifecycle & Post Processors      🔄
07 Advanced Autowiring                   ⏳
08 Advanced BeanPostProcessor            ⏳
09 Spring AOP                            ⏳
```

---

# 📚 Concepts Covered

```text
Spring IoC Container
ApplicationContext
BeanDefinition
BeanFactoryPostProcessor
BeanPostProcessor
Bean Lifecycle
Bean Instantiation
Dependency Injection
@PostConstruct
InitializingBean
initMethod
@PreDestroy
DisposableBean
destroyMethod
Bean Initialization
Bean Destruction
Bean Processing
Spring Extension Points
```

---

## 👨‍💻 Learning Goal

This project is part of my journey to learn **Spring Framework from Core concepts to Spring Boot and Microservices**.

The focus is on understanding how Spring works internally by building practical projects instead of relying only on theoretical explanations.
