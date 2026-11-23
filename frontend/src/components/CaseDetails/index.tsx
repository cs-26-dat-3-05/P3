import styled from "styled-components";
import AktuelleTrin from "../AktuelleTrin";
import InternalNotes from "../InternalNotes";
import type { CaseItem } from "../Sagsoversigt/caseData";

const CaseDetailsWrapper = styled.div`
  display: grid;
  grid-template-columns: 3fr 2fr;
  gap: 24px;
  margin-top: 24px;
`;

export default function CaseDetails({ caseItem }: { caseItem: CaseItem }) {
  return (
    <CaseDetailsWrapper>
      <AktuelleTrin step={caseItem.step} progress={caseItem.progress} />
      <InternalNotes note={caseItem.note} />
    </CaseDetailsWrapper>
  );
}
