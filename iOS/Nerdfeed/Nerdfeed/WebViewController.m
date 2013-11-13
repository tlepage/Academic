//
//  WebViewController.m
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "WebViewController.h"
#import "RSSItem.h"

@implementation WebViewController

- (void)loadView
{
    // Create an instance of UIWebView as large as the screen
    CGRect screenFrame = [[UIScreen mainScreen] applicationFrame];
    UIWebView *wv = [[UIWebView alloc] initWithFrame:screenFrame];
    // Tell the web view to scale web content to fit within the bounds of webview
    [wv setScalesPageToFit:YES];
    
    [self setView:wv];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)
    {
        return YES;
    }
    
    return toInterfaceOrientation == UIInterfaceOrientationPortrait;
}

- (void)listViewController:(ListViewController *)lvc handleObject:(id)object
{
    // Cast the passed object to RSSItem
    RSSItem *entry = object;
    
    // Make sure that we are really getting an RSSItem
    if (![entry isKindOfClass:[RSSItem class]])
    {
        return;
    }
    
    // Grab the info from the item and push it into the appropriate views
    NSURL *url = [NSURL URLWithString:[entry link]];
    NSURLRequest *req = [NSURLRequest requestWithURL:url];
    [[self webView] loadRequest:req];
    
    [[self navigationItem] setTitle:[entry title]];
}

- (UIWebView *)webView
{
    return (UIWebView *)[self view];
}

- (void)splitViewController:(UISplitViewController *)svc willHideViewController:(UIViewController *)aViewController withBarButtonItem:(UIBarButtonItem *)barButtonItem forPopoverController:(UIPopoverController *)pc
{
    // If this bar button item doesn't have a title, it won't appear at all
    [barButtonItem setTitle:@"List"];
    
    // Take this bar button item and put it on the left side of our nav item
    [[self navigationItem] setLeftBarButtonItem:barButtonItem];
}

- (void)splitViewController:(UISplitViewController *)svc willShowViewController:(UIViewController *)aViewController invalidatingBarButtonItem:(UIBarButtonItem *)barButtonItem
{
    // Remove the bar button item from our navigation item
    // Double check that its the correct button, even though we know it is
    if (barButtonItem == [[self navigationItem] leftBarButtonItem])
    {
        [[self navigationItem] setLeftBarButtonItem:nil];
    }
}

@end
