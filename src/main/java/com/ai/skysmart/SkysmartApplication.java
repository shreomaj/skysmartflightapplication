package com.ai.skysmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkysmartApplication {

	public static void main(String[] args) {

        SpringApplication.run(SkysmartApplication.class, args);
	}
///flights/search
///booking/create
///payment/verify
///support/chat
//For CRUD operations:
//    Flight info
//    User details
//    Booking records
//    Payment logs
//Validate user inputs:
//    Email, Passport number, Flight booking form
//    Spring AOP
//
//    Useful for:
//
//    Logging
//
//    Method-level performance tracking
//
//    Auditing actions (e.g., who booked what and when)
//    Spring Scheduler
//
//    For scheduled tasks like:
//
//    Auto-cancel unpaid bookings after 30 mins
//
//    Send daily flight reminders
//
//    Nightly data cleanup
//    Caching (Spring Cache + Redis or EhCache)
//
//    Use caching for:
//
//    Flight list
//
//    Search results
//
//    Seat availability
//
//    Improves performance significantly.
//            Email & Notification Support
//
//    Use:
//
//    Spring Mail for booking confirmations
//
//    Twilio for SMS reminders
//    AI Integration (LLM API calls)
//
//    These features bring your project to the next level:
//
//            ✔ AI Chatbot (Customer Support)
//
//    Using:
//
//    Spring WebClient
//
//    OpenAI / Gemini API
//
//    Chatbot tasks:
//
//    Booking help
//
//    Flight status queries
//
//    Refund guidance
//
//✔ Intelligent Search (LLM-based)
//
//    Example:
//
//    User types: "Show me morning flights from Delhi to Dubai under 10k"
//
//    LLM converts this to structured filters
//
//    Backend returns accurate results
//
//    This is LLM-to-SQL or Semantic Search.
//
//            ✔ RAG for Support Knowledge Base
//
//    Use:
//
//    Vector DB (Elastic, Pinecone, PostgreSQL pgvector)
//
//    Spring Boot REST API to retrieve vectors
//
//    Store:
//
//    Refund policies
//
//    Flight rules
//
//    Cancellation rules
//
//    When user asks:
//
//            “Can I reschedule my ticket for free?”
//
//    Your system searches policy documents and returns answers.
//
//            ✔ AI Content Generation
//
//    Examples:
//
//    Auto-generate trip recommendations
//
//    Personalized travel summaries
//
//    FAQ responses
//
//✔ Data Extraction (from PDFs)
//
//    Use:
//
//    Apache PDFBox + LLM API
//
//    Tasks:
//
//    Extract passenger info from uploaded documents
//
//    Parse travel itineraries
}
