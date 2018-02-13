package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.apache.commons.csv.*;

/**
 * Unit test for simple App.
 */
public class MailReaderMapperTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MailReaderMapperTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MailReaderMapperTest.class );
    }

    /**
     * Quick test for procRecipients to avoid running via the HDFS cluster
     */
    public void testProcRecipients()
    {
	    ArrayList<String> testRecipients = new ArrayList<String>();

	    MailReaderMapper.procRecipients("foo@example.com, george.mcclellan@enron.com, daniel.reck@enron.com, stuart.staley@enron.com, \n\tmichael.beyer@enron.com, bar@example.com, kevin.mcgowan@enron.com,\n\tjeffrey.shankman@enron.com, mike.mcconnell@enron.com,\n\tbaz@example.com, paula.harris@enron.com", testRecipients);

	    // System.out.println(String.join(", ", testRecipients));
	    assertTrue(testRecipients.size() == 8);
    }

    public void testCSVRead() throws IOException
    {
	    HashMap<String, String> testPositions;
	    testPositions = MailReaderMapper.readEmployeePositions();

	    assertTrue(testPositions.size() == 160);
    }
}
