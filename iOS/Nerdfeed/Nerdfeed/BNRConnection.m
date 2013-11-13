//
//  BNRConnection.m
//  Nerdfeed
//
//  Created by Tom LePage on 10/8/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "BNRConnection.h"

static NSMutableArray *sharedConnectionList;

@implementation BNRConnection
@synthesize request, completionBlock, xmlRootObject, jsonRootObject;

- (id)initWithRequest:(NSURLRequest *)req
{
    self = [super init];
    
    if (self)
    {
        [self setRequest:req];
    }
    
    return self;
}

- (void)start
{
    // Initialize container for data collected from NSURLConnection
    container = [[NSMutableData alloc] init];
    
    // Spawn connection
    internalConnection = [[NSURLConnection alloc] initWithRequest:[self request] delegate:self startImmediately:YES];
    
    // If this is the first connection started, create the array
    if (!sharedConnectionList)
    {
        sharedConnectionList = [[NSMutableArray alloc] init];
    }
    
    // Add the connection to the array so that it doesn't get destroyed
    [sharedConnectionList addObject:self];
    NSLog(@"Started...");
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [container appendData:data];
    NSLog(@"Appended data...");
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    id rootObject = nil;
    // If there is a root object...
    if ([self xmlRootObject])
    {
        // Create a parser with the incoming data and let the root object parse its contents
        NSXMLParser *parser = [[NSXMLParser alloc] initWithData:container];
        [parser setDelegate:[self xmlRootObject]];
        [parser parse];
        rootObject = [self xmlRootObject];
        NSLog(@"Parsing...");
    }
    else if ([self jsonRootObject])
    {
        // Turn JSON data into basic model objects
        NSDictionary *d = [NSJSONSerialization JSONObjectWithData:container options:0 error:nil];
        
        // Have the root object construct itself from basic model objects
        [[self jsonRootObject] readFromJSONDictionary:d];
        
        rootObject = [self jsonRootObject];
    }
    
    // Then pass the root object to the completion block - this is the block that the controller supplied
    if ([self completionBlock])
    {
        //[self completionBlock]([self xmlRootObject], nil);
        [self completionBlock](rootObject, nil);
    }
    
    // Destroy the connection
    [sharedConnectionList removeObject:self];
    NSLog(@"Finished...");
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    // Pass the error from the connection to the completionBlock
    if ([self completionBlock])
    {
        [self completionBlock](nil, error);
    }
    
    // Destroy the connection
    [sharedConnectionList removeObject:self];
}

@end
