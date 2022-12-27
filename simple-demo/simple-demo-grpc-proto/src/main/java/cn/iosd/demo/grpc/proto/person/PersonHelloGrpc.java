package cn.iosd.demo.grpc.proto.person;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: person.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PersonHelloGrpc {

  private PersonHelloGrpc() {}

  public static final String SERVICE_NAME = "PersonHello";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<PersonHelloRequest,
      PersonHelloReply> getPersonSayHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PersonSayHello",
      requestType = PersonHelloRequest.class,
      responseType = PersonHelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<PersonHelloRequest,
      PersonHelloReply> getPersonSayHelloMethod() {
    io.grpc.MethodDescriptor<PersonHelloRequest, PersonHelloReply> getPersonSayHelloMethod;
    if ((getPersonSayHelloMethod = PersonHelloGrpc.getPersonSayHelloMethod) == null) {
      synchronized (PersonHelloGrpc.class) {
        if ((getPersonSayHelloMethod = PersonHelloGrpc.getPersonSayHelloMethod) == null) {
          PersonHelloGrpc.getPersonSayHelloMethod = getPersonSayHelloMethod =
              io.grpc.MethodDescriptor.<PersonHelloRequest, PersonHelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PersonSayHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PersonHelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PersonHelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new PersonHelloMethodDescriptorSupplier("PersonSayHello"))
              .build();
        }
      }
    }
    return getPersonSayHelloMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PersonHelloStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonHelloStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonHelloStub>() {
        @Override
        public PersonHelloStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonHelloStub(channel, callOptions);
        }
      };
    return PersonHelloStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PersonHelloBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonHelloBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonHelloBlockingStub>() {
        @Override
        public PersonHelloBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonHelloBlockingStub(channel, callOptions);
        }
      };
    return PersonHelloBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PersonHelloFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonHelloFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonHelloFutureStub>() {
        @Override
        public PersonHelloFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonHelloFutureStub(channel, callOptions);
        }
      };
    return PersonHelloFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PersonHelloImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void personSayHello(PersonHelloRequest request,
                               io.grpc.stub.StreamObserver<PersonHelloReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPersonSayHelloMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPersonSayHelloMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                PersonHelloRequest,
                PersonHelloReply>(
                  this, METHODID_PERSON_SAY_HELLO)))
          .build();
    }
  }

  /**
   */
  public static final class PersonHelloStub extends io.grpc.stub.AbstractAsyncStub<PersonHelloStub> {
    private PersonHelloStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonHelloStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonHelloStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void personSayHello(PersonHelloRequest request,
                               io.grpc.stub.StreamObserver<PersonHelloReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPersonSayHelloMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PersonHelloBlockingStub extends io.grpc.stub.AbstractBlockingStub<PersonHelloBlockingStub> {
    private PersonHelloBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonHelloBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonHelloBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public PersonHelloReply personSayHello(PersonHelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPersonSayHelloMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PersonHelloFutureStub extends io.grpc.stub.AbstractFutureStub<PersonHelloFutureStub> {
    private PersonHelloFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonHelloFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonHelloFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<PersonHelloReply> personSayHello(
        PersonHelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPersonSayHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PERSON_SAY_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PersonHelloImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PersonHelloImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PERSON_SAY_HELLO:
          serviceImpl.personSayHello((PersonHelloRequest) request,
              (io.grpc.stub.StreamObserver<PersonHelloReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PersonHelloBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PersonHelloBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AddressBookProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PersonHello");
    }
  }

  private static final class PersonHelloFileDescriptorSupplier
      extends PersonHelloBaseDescriptorSupplier {
    PersonHelloFileDescriptorSupplier() {}
  }

  private static final class PersonHelloMethodDescriptorSupplier
      extends PersonHelloBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PersonHelloMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PersonHelloGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PersonHelloFileDescriptorSupplier())
              .addMethod(getPersonSayHelloMethod())
              .build();
        }
      }
    }
    return result;
  }
}
