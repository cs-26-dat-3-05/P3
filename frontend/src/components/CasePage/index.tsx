import { useState } from "react";
import CaseCards from "../CaseCards";
import CaseDetails from "../CaseDetails";
import Emails from "../Emails/IndexEmail";
import { BackLink, PageHeader, CaseMeta, CaseTitle, EditButton, Tabs, Tab } from "./style";
import type { CaseItem } from "../Sagsoversigt/caseData";

type CasePageProps = {
    caseItem: CaseItem;
    onBack: () => void;
};

export default function CasePage({ caseItem, onBack }: CasePageProps) {
    const [activeTab, setActiveTab] = useState<"sagsoversigt" | "email">("sagsoversigt");
    return (
        <>
            <BackLink href="#" onClick={onBack}>← TILBAGE TIL SAGER</BackLink>
            
            <PageHeader>
                <div>
                    <CaseMeta>{caseItem.id} [ {caseItem.status.toUpperCase()} ]</CaseMeta>
                    <CaseTitle>{caseItem.organizer}</CaseTitle>
                </div>
                <EditButton>REDIGÉR OPLYSNINGER</EditButton>
            </PageHeader>
            
            <CaseCards caseItem={caseItem} />
            
            <Tabs>
                <Tab $active={activeTab === "sagsoversigt"} onClick={() => setActiveTab("sagsoversigt")}>SAGSOVERSIGT</Tab>
                <Tab $active={activeTab === "email"} onClick={() => setActiveTab("email")}>EMAIL (2)</Tab>
            </Tabs>
            
            {activeTab === "sagsoversigt" && <CaseDetails caseItem={caseItem} />}
            {activeTab === "email" && <Emails />}
        </>
    )
}
