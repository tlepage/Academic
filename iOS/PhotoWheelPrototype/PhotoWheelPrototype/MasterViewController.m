//
//  MasterViewController.m
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "MasterViewController.h"
#import "DetailViewController.h"
#import "PhotoAlbum.h"

@interface MasterViewController ()
{
    NSMutableArray* _objects;
}
@property (readwrite, assign) NSUInteger currentAlbumIndex;
@end

@implementation MasterViewController

@synthesize data = _data;
@synthesize managedObjectContext = _managedObjectContext;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"Photo Albums", @"Photo albums title");
        self.clearsSelectionOnViewWillAppear = NO;
        self.contentSizeForViewInPopover = CGSizeMake(320.0, 600.0);
    }
    return self;
}
							
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    self.navigationItem.leftBarButtonItem = self.editButtonItem;

    UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(add:)];
    self.navigationItem.rightBarButtonItem = addButton;
    
    self.title = NSLocalizedString(@"Photo Albums", @"Photo albums title");
    
    //[self setData:[[NSMutableOrderedSet alloc] init]];
    //[[self data] addObject:@"A Sample Photo Album"];
    //[[self data] addObject:@"Another Photo Album"];
    
    [self setData: [PhotoAlbum allPhotoAlbumsInContext:[self managedObjectContext]]];
    
    if ([[self data] count] == 0)
    {
        PhotoAlbum *newAlbum = [PhotoAlbum newPhotoAlbumWithName:@"First Album" inContext:[self managedObjectContext]];
        [self setData:[NSMutableArray arrayWithObject:newAlbum]];
        [[self managedObjectContext] save:nil];
    }
    
    [self.navigationItem setLeftBarButtonItem:[self editButtonItem]];
    
    [[self detailViewController] setPhotoAlbum:[[self data] objectAtIndex:0]];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

/*
- (void)insertNewObject:(id)sender
{
    if (!_objects) {
        _objects = [[NSMutableArray alloc] init];
    }
    [_objects insertObject:[NSDate date] atIndex:0];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
    [self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}
*/

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSInteger count = self.data.count;
    return count;
    //return _objects.count;
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        
        [cell setEditingAccessoryType:UITableViewCellAccessoryDetailDisclosureButton];
        [cell setShowsReorderControl:YES];
    }

    PhotoAlbum *album = [[self data] objectAtIndex:[indexPath row]];
    [[cell textLabel] setText:[album name]];
    
    if ([indexPath row] == [self currentAlbumIndex])
    {
        [cell setAccessoryType:UITableViewCellAccessoryCheckmark];
    }
    else
    {
        [cell setAccessoryType:UITableViewCellAccessoryNone];
    }
    //NSString *text = [self.data objectAtIndex:[indexPath row]];
    //[cell.textLabel setText:text];
    //return cell;

    //NSDate *object = [_objects objectAtIndex:indexPath.row];
    //cell.textLabel.text = [object description];
    return cell;
}

- (void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath
{
    NameEditorViewController *newController = [[NameEditorViewController alloc] initWithDefaultNib];
    
    [newController setDelegate:self];
    [newController setEditing:YES];
    [newController setIndexPath:indexPath];
    NSString *name = [[[self data] objectAtIndex:[indexPath row]] valueForKey:@"name"];
    [newController setDefaultNameText:name];
    [newController setModalPresentationStyle:UIModalPresentationFormSheet];
    [self presentModalViewController:newController animated:YES];
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete)
    {
        [[self data] removeObjectAtIndex:[indexPath row]];
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)sourceIndexPath toIndexPath:(NSIndexPath *)destinationIndexPath
{
    [self.data exchangeObjectAtIndex:destinationIndexPath.row withObjectAtIndex:sourceIndexPath.row];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSIndexPath *oldCurrentAlbumIndexPath = [NSIndexPath indexPathForRow:[self currentAlbumIndex] inSection:0];
    
    [self setCurrentAlbumIndex:[indexPath row]];
    [tableView reloadRowsAtIndexPaths:[NSArray arrayWithObjects:indexPath, oldCurrentAlbumIndexPath, nil] withRowAnimation:UITableViewRowAnimationNone];
    
    PhotoAlbum *selectedAlbum = [[self data] objectAtIndex:[indexPath row]];
    [[self detailViewController] setPhotoAlbum:selectedAlbum];
}

- (void)add:(id)sender
{
    NameEditorViewController *newController = [[NameEditorViewController alloc] initWithDefaultNib];
    [newController setDelegate:self];
    [newController setModalPresentationStyle:UIModalPresentationFormSheet];
    [self presentModalViewController:newController animated:YES];
}

#pragma mark - NameEditorViewControllerDelegate

- (void)nameEditorViewControllerDidFinish:(NameEditorViewController *)controller
{
    NSString *newName = [controller.nameTextField text];
    if (newName && [newName length] > 0)
    {
        if ([controller isEditing])
        {
            PhotoAlbum *album = [[self data] objectAtIndex:[[controller indexPath] row]];
            [album setName:newName];
        }
        else
        {
            PhotoAlbum *newAlbum = [PhotoAlbum newPhotoAlbumWithName:newName inContext:[self managedObjectContext]];
            [[self data] addObject:newAlbum];
        }
        
        [[self managedObjectContext] save:nil];
        [self.tableView reloadData];
    }
}

- (void)nameEditorViewControllerDidCancel:(NameEditorViewController *)controller
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
}

@end
