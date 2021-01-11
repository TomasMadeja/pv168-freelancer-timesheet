# PV168: Timesheet for Freelancer [![Build Status](https://travis-ci.com/TomasMadeja/pv168-freelancer-timesheet.svg?token=c42PmsJVx99AtxxtBo96&branch=main)](https://travis-ci.com/TomasMadeja/pv168-freelancer-timesheet)

### Team Members

* Jakub Kolárovský [IS](https://is.muni.cz/auth/osoba/485605) [GitHub](https://github.com/dermonter)
* Tomáš Madeja [IS](https://is.muni.cz/auth/osoba/456443) [GitHub](https://github.com/TomasMadeja)
* Martin Mojžíšek [IS](https://is.muni.cz/auth/osoba/485242) [GitHub](https://github.com/Megaranator)
* Samuel Špalek [IS](https://is.muni.cz/auth/osoba/493013) [GitHub](https://github.com/Zyfix)

## Assignament Specification

The application will allow for recording work done by the freelancer for clients. The
application should support:  

* Evidence of work done. Each time entry includes:
    * Start time
    * End time
    * Work type
* Edit time entry:
    * Modify the start time and/or end time
    * Modify the work type
* Add/Modify work type. Each work type includes:
    * Short description
    * Hourly rate
    * Calculate income for last period.
* Create an invoice. Invoice will contain
    * Identification of the client
    * Identification of the issuer
    * Date issued
    * Due date
    * Specification of work done, including hourly rate and number of hours
    * Total amount

Application should have a standard user-friendly GUI that will show basic information
about all issued invoices in the last year and the total income for the particular year.

## Deadline 1: GUI for MVP (2020-11-13T23:59:00+01:00)

Tasks:

* Samuel Špalek
    * Task Tab
* Martin Mojžíšek
    * Task Tab -> Work Type selection popup
* Tomáš Madeja: 
    * Invoice Tab
* Jakub Kolárovský
    * Invoice Tab -> Work selection popup

Notes:
* ADD/VIEW/DELETE buttons don't possess their full functionality
    * ADD doesn't have to create records (requires backend)
    * VIEW doesn't have to open records (requires backend)
    * VIEW on invoice doesn't do anything (excepted functionallity is to open related pdf invoice)
    * DELETE button doesn't have to remove records (requires backend)
* Autocalculated fields in Invoice do not work yet (backend Invoice class should provide the calculaction)

## Deadlines 2 and onward

* For tasks past Deadline 1, see [github issues](https://github.com/TomasMadeja/pv168-freelancer-timesheet/issues?q=is%3Aissue+)