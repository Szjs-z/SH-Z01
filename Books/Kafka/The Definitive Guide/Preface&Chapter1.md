# Preface

The greatest compliment you can give an author of a technical book is “This is the book I wish I had when I got started with this subject.” This is the goal we set for ourselves when we started writing this book. 

we asked ourselves, “What are the most useful things we can share with new users to take them from beginner to expert?” This book is a reflection of the work we do every day: run Apache Kafka and help others use it in the best ways.

# Chapter 1. Meet Kafka

## Publish/Subscribe Messaging

*Publish/subscribe (pub/sub) messaging* is a pattern that is characterized by the sender (publisher) of a piece of data (message) not specifically directing it to a receiver. Instead, the publisher classifies the message somehow, and that receiver (subscriber) subscribes to receive certain classes of messages.

## Enter Kafka

### Messages and Batches

The unit of data within Kafka is called a *==message==*. A message is simply an array of bytes as far as Kafka is concerned,  A message can have an optional piece of metadata, which is referred to as a *key*. The key is also a byte array. Keys are used when messages are to be written to partitions in a more controlled manner. 

The simplest such scheme is to generate a consistent hash of the key and then select the partition number for that message by taking the result of the hash modulo the total number of partitions in the topic. This ensures that messages with the same key are always written to the same partition (provided that the partition count does not change).

For efficiency, messages are written into Kafka in ==batches==. A *batch* is just a collection of messages, all of which are being produced to the same topic and partition. this is a trade-off between latency and throughput: the larger the batches, the more messages that can be handled per unit of time, but the longer it takes an individual message to propagate.



### Schamas

While messages are opaque byte arrays to Kafka itself, it is recommended that additional structure, or schema, be imposed on the message content so that it can be easily understood.

There are many options available for message *schema*, depending on your application’s individual needs. (Json、XML..).

Many Kafka developers favor the use of Apache Avro. Avro provides a compact serialization format, <u>schemas that are separate from the message payloads and that do not require code to be generated when they change, and strong data typing and schema evolution, with both backward and forward compatibility</u>.

A consistent data format is important in Kafka, as it allows writing and reading messages to be decoupled. When these tasks are tightly coupled, applications that subscribe to messages must be updated to handle the new data format, in parallel with the old format. Only then can the applications that publish the messages be updated to utilize the new format. By using well-defined schemas and storing them in a common repository, the messages in Kafka can be understood without coordination.

### Topics and Partitions

Messages in Kafka are categorized into *==topics==*. Topics are additionally broken down into a number of *==partitions==*.

Going back to the “commit log” description, a partition is a single log. Messages are written to it in an append-only fashion and are read in order from beginning to end.

**Note that as a topic typically has multiple partitions, there is no guarantee of message ordering across the entire topic, just within a single partition.**

 Partitions are also the way that Kafka provides redundancy and scalability. <u>Each partition can be hosted on a different server</u>, which means that a single topic can be scaled horizontally across multiple servers to provide performance far beyond the ability of a single server. Additionally, <u>partitions can be replicated</u>, such that different servers will store a copy of the same partition in case one server fails.

 A stream is considered to be a single topic of data, regardless of the number of partitions. This represents a single stream of data moving from the producers to the consumers.

### Producers and Consumers

Kafka clients are users of the system, and there are two basic types: producers and consumers. 

*==Producers==* create new messages. In other publish/subscribe systems. A message will be produced to a specific topic. By default, the producer will <u>balance messages over all partitions of a topic evenly</u>. In some cases, the producer will direct <u>messages to specific partitions</u>. This is typically done using the message key and a partitioner that will generate a hash of the key and map it to a specific partition. This ensures that all messages produced with a given key will get written to the same partition. The producer could also use a <u>custom partitioner</u> that follows other business rules for mapping messages to partitions.

*==Consumers==* read messages. The consumer subscribes to one or more topics and reads the messages in the order in which they were produced to each partition. 
