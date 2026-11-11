import styled from "styled-components";
import AktuelleTrin from "../AktuelleTrin";
import InternalNotes from "../InternalNotes";

const CaseDetailsWrapper = styled.div`
  display: grid;
  grid-template-columns: 3fr 2fr;
  gap: 24px;
  margin-top: 24px;
`;

export default function CaseDetails() {
  return (
    <CaseDetailsWrapper>
      <AktuelleTrin></AktuelleTrin>
      <InternalNotes></InternalNotes>
    </CaseDetailsWrapper>
  );
}
