import styled from "styled-components";
import Header from "./components/Header";
import KpiCard from "./components/KpiCard";
const Heading = styled.h1`
  color: var(--primary-color);
`;
import CaseCards from "./components/CaseCards";
import SendMail from "./components/Emails/SendMail";
import type { emailsObject } from "./components/Emails/EmailTråd";
import type { email } from "./components/Emails/EmailTråd";
import EmailCorrespondance from "./components/Emails/EmailTråd";

export default function App() {
  return (
    <>
    <Header />
    <main className="page">
      <Heading>Aktive sager</Heading>
      <CaseCards></CaseCards>
      <EmailCorrespondance theEmails={dummyDataEmails}></EmailCorrespondance>
    </main>
    </>
  );
}

/* 

*/


const dummyDataEmails: emailsObject = {
  AllEmailsArray: [
    {
    mailType: "Påmindelse",
    mailStatus: "Systemmail afsendt",
    receiver: "mette@aalborg.dk",
    sender: "Medarbejder1",
    date: new Date(),
    note: "Automatisk påmindelse om at oplyse kontonummer",
    emailBody: "Hej Valdemar, vi minder dig om at vi stadig mangler et nyt kontonummer"
    },
    {
      mailType: "Bankoplysninger",
      mailStatus: "Systemmail afsendt",
      receiver: "netto@aalborg.dk",
      sender: "Medarbejder1",
      date: new Date(),
      note: "Anmodning om nye bankoplysninger sendt automatisk",
      emailBody: "Hej Valdemar, du har et forkert kontonummer"
    }
  ]
}

