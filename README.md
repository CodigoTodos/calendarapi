# Build an interview calendar API

There may be two roles that use this API, a candidate and an interviewer. A typical scenario is when:

An interview slot is a 1-hour period of time that spreads from the beginning of any hour until the beginning of the next hour. For example, a time span between 9am and 10am is a valid interview slot, whereas between 9:30am and 10:30am is not.

Each of the interviewers sets their availability slots. For example, the interviewer Ines is available next week each day from 9am through 4pm without breaks and the interviewer Ingrid is available from 12pm to 6pm on Monday and Wednesday next week, and from 9am to 12pm on Tuesday and Thursday.

Each of the candidates sets their requested slots for the interview. For example, the candidate Carl is available for the interview from 9am to 10am any weekday next week and from 10am to 12pm on Wednesday.

Anyone may then query the API to get a collection of periods of time when it’s possible to arrange an interview for a particular candidate and one or more interviewers. In this example, if the API queries for the candidate Carl and interviewers Ines and Ingrid, the response should be a collection of 1-hour slots: from 9am to 10am on Tuesday, from 9am to 10am on Thursday.

# Installation

Have docker installed and running. 

# Build

Pull the Docker Image

```
docker pull bmpalves/interviewcalendarapi:latest
```

Run the command 
```
docker run -p 8080/8080 bmpalves/interviewcalendarapi
```

Should be possible to access the API from the port 8080.

## API

# Api Documentation
Api Documentation

## Version: 1.0

**License:** [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)

### /persons

#### GET
##### Summary:

Find all persons (Candidates and Interviewers)

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ [Person](#Person) ] |

#### POST
##### Summary:

Creates a New Person

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| person | body | person | Yes | [Person](#Person) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Created | [Person](#Person) |

### /persons/{id}

#### GET
##### Summary:

Find Person by ID

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | integer |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Person](#Person) |

#### PUT
##### Summary:

Updates a Person by ID

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| person | body | person | Yes | [Person](#Person) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Person](#Person) |

#### DELETE
##### Summary:

Deletes a Person by ID

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /availability/{personsIdList}

#### GET
##### Summary:

Finds common availability for a candidate and one or more interviewers.In the personsIdList the first position should be the candidate id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| personsIdList | path | personsIdList | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ [AvailabilitySlot](#AvailabilitySlot) ] |

### Models


#### Person

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | integer | The Person unique ID | No |
| name | string |The person name   | Yes |
| personType | string |The type of person: INTERVIEWER or CANDIDATE  | Yes |
| availabilitySlotsList | [ [AvailabilitySlot](#AvailabilitySlot) ] | Free availability slots list | Yes |

#### AvailabilitySlot

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | integer |The slot unique ID  | No |
| dayOfWeek | string | Day of Week (Ex:MONDAY)  | Yes |
| startTime | string | The start Time (HH:mm) | Yes |
| finishTime | string | The finish Time (HH:mm)  | Yes |


