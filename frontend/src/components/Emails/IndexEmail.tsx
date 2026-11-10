import EmailBox from "./SendMail";
import EmailCorrespondence from "./EmailCorrespondence";
import type { emailsObject } from "./EmailCorrespondence";
import styled from "styled-components";

const Combined = styled.div`
    display: flex;
    gap: 20px;
`;


function EmailLayout(){
    return (
        <Combined>
            <EmailCorrespondence theEmails={dummyDataEmails}></EmailCorrespondence>
            <EmailBox></EmailBox>
        </Combined>
    );
}

export default EmailLayout;

const dummyDataEmails: emailsObject = {
  AllEmailsArray: [
    {
    mailType: "PÅMINDELSE",
    mailStatus: "SYSTEMMAIL AFSENDT",
    receiver: "mette@aalborg.dk",
    sender: "Medarbejder1",
    date: new Date(),
    note: "Automatisk påmindelse om at oplyse kontonummer",
    emailBody: "Hej Valdemar, vi minder dig om at vi stadig mangler et nyt kontonummer"
    },
    {
      mailType: "BANKOPLYSNINGER",
      mailStatus: "SYSTEMMAIL AFSENDT",
      receiver: "netto@aalborg.dk",
      sender: "Medarbejder1",
      date: new Date(),
      note: "Anmodning om nye bankoplysninger sendt automatisk",
      emailBody: "Hej Valdemar, du har et forkert kontonummer"
    }
  ]
}

