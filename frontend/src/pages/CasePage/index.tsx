import { useState } from "react";
import { useParams } from "react-router";
import CaseCards from "../../components/CaseCards";
import CaseDetails from "../../components/CaseDetails";
import Emails from "../../components/Emails/IndexEmail";
import { useCase } from "../../hooks/useCase";
import { useNotes } from "../../hooks/useNotes";
import { STATUS_MAP } from "../../constants/caseStatus";
import {
  BackLink,
  PageHeader,
  CaseMeta,
  CaseTitle,
  EditButton,
  Tabs,
  Tab,
} from "./style";

type TabKey = "sagsoversigt" | "email";

export default function CasePage() {
  const { caseId } = useParams();
  const id = Number(caseId);
  const [activeTab, setActiveTab] = useState<TabKey>("sagsoversigt");

  const { caseData, loading, error } = useCase(id);
  const { notes } = useNotes(id);

  if (!Number.isFinite(id)) return <p>Ugyldigt sags-id</p>;
  if (loading) return <p>Indlæser…</p>;
  if (error || !caseData) return <p>Kunne ikke hente sagen</p>;

  const statusLabel = STATUS_MAP[caseData.status]?.label ?? caseData.status;

  return (
    <>
      <BackLink to="/">← Tilbage til oversigt</BackLink>

      <PageHeader>
        <div>
          <CaseMeta>
            RP-{caseData.organizerId} [ {statusLabel.toUpperCase()} ]
          </CaseMeta>
          <CaseTitle>{caseData.organizerName ?? "Ukendt arrangør"}</CaseTitle>
        </div>
        <EditButton>REDIGÉR OPLYSNINGER</EditButton>
      </PageHeader>

      <CaseCards caseData={caseData} />

      <Tabs>
        <Tab
          $active={activeTab === "sagsoversigt"}
          onClick={() => setActiveTab("sagsoversigt")}
        >
          SAGSOVERSIGT
        </Tab>
        <Tab
          $active={activeTab === "email"}
          onClick={() => setActiveTab("email")}
        >
          EMAIL
        </Tab>
      </Tabs>

      {activeTab === "sagsoversigt" && (
        <CaseDetails caseData={caseData} notes={notes} />
      )}
      {activeTab === "email" && <Emails />}
    </>
  );
}
