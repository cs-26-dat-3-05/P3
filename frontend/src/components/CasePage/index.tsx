import { useState } from "react";
import CaseCards from "../CaseCards";
import CaseDetails from "../CaseDetails";
import Emails from "../Emails/IndexEmail";
import { BackLink, PageHeader, CaseMeta, CaseTitle, EditButton, Tabs, Tab } from "./style";

interface Case {
    id: string;
    status: string;
    organizer: string;
}

const MOCK_CASE: Case = {
    id: "RP-10XX",
    status: "Afventer",
    organizer: "Aalborg Kommune"
};

type CasePageProps = {
    onBack: () => void
};

export default function CasePage({ onBack }: CasePageProps) {
    const [activeTab, setActiveTab] = useState<"sagsoversigt" | "email">("sagsoversigt");
    return (
        <>
            <BackLink href="#" onClick={onBack}>← TILBAGE TIL SAGER</BackLink>
            
            <PageHeader>
                <div>
                    <CaseMeta>{MOCK_CASE.id} [ {MOCK_CASE.status.toUpperCase()} ]</CaseMeta>
                    <CaseTitle>{MOCK_CASE.organizer}</CaseTitle>
                </div>
                <EditButton>REDIGÉR OPLYSNINGER</EditButton>
            </PageHeader>
            
            <CaseCards />
            
            <Tabs>
                <Tab $active={activeTab === "sagsoversigt"} onClick={() => setActiveTab("sagsoversigt")}>SAGSOVERSIGT</Tab>
                <Tab $active={activeTab === "email"} onClick={() => setActiveTab("email")}>EMAIL (2)</Tab>
            </Tabs>
            
            {activeTab === "sagsoversigt" && <CaseDetails />}
            {activeTab === "email" && <Emails />}
        </>
    )
}