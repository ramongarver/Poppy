import json
import random

MIN_USER_ID = 2
MAX_USER_ID = 20

MIN_NUM_ACTIVITIES = 1
MAX_NUM_ACTIVITIES = 14

MIN_ACTIVITY_ID = 3
MAX_ACTIVITY_ID = 24

ACTIVITY_PACKAGE_ID = 4

def generate_activities():
    num_activities = random.randint(MIN_NUM_ACTIVITIES, MAX_NUM_ACTIVITIES)
    activity_ids_set = set()

    while len(activity_ids_set) < num_activities:
        activity_id = random.randint(MIN_ACTIVITY_ID, MAX_ACTIVITY_ID)
        activity_ids_set.add(activity_id)

    activity_ids = sorted(list(activity_ids_set))
    return activity_ids

def transform_activities(user_activities):
    transformed_list = []

    for user_id, activity_ids in user_activities.items():
        package = {
            "volunteer": {"id": user_id},
            "activityPackage": {"id": ACTIVITY_PACKAGE_ID},
            "activities": [{"id": activity_id} for activity_id in activity_ids]
        }

        transformed_list.append(package)

    return transformed_list

user_activities = {}
for user_id in range(MIN_USER_ID, MAX_USER_ID + 1):
    activity_ids = generate_activities()
    user_activities[user_id] = activity_ids

transformed_list = transform_activities(user_activities)

# Save the transformed list to a JSON file
with open("volunteerAvailabilities.json", "w") as file:
    json.dump(transformed_list, file, indent=4)

print("File 'volunteerAvailabilities.json' successfully generated.")